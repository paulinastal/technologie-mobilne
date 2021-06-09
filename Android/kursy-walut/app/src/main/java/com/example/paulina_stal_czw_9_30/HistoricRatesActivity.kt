package com.example.paulina_stal_czw_9_30

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import org.json.JSONObject

class HistoricRatesActivity : AppCompatActivity() {
    private lateinit var todayRate: TextView
    private lateinit var yesterdayRate: TextView
    private lateinit var lineChart: LineChart
    private lateinit var weekLineChart: LineChart
    private lateinit var currencyCode: String
    private lateinit var data: Array<Pair<String, Double>>
    private lateinit var tableName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historic_rates)
        yesterdayRate = findViewById(R.id.yesterdayRate)
        todayRate = findViewById(R.id.todayRate)
        lineChart = findViewById(R.id.historicRatesChart)
        weekLineChart = findViewById(R.id.historicWeekRatesChart)

        currencyCode = intent.getStringExtra("currencyCode")!!
        tableName = intent.getStringExtra("tableName")?:" "

        title = currencyCode

        lineChart.legend.isEnabled = true
        lineChart.description.isEnabled = false
        lineChart.xAxis.labelRotationAngle = -90f
        lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        lineChart.extraBottomOffset = 60f

        weekLineChart.legend.isEnabled = true
        weekLineChart.description.isEnabled = false
        weekLineChart.xAxis.labelRotationAngle = -90f
        weekLineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        weekLineChart.extraBottomOffset = 60f

        getHistoricRates()
    }

    private fun getHistoricRates() {
        val queue = DataHolder.queue
        val url = "http://api.nbp.pl/api/exchangerates/rates/%s/%s/last/30?format=json".format(tableName, currencyCode)

        val historicRatesRequest = JsonObjectRequest( Request.Method.GET, url, null,
        Response.Listener { response ->
            println("Success!")
            loadHistoricData(response)
            showWeekData()
            showData()
        }, Response.ErrorListener {
            error ->
            Log.d("rates:fetchError", error.toString())
            todayRate.text = "Wystąpił problem z pobraniem danych"
            yesterdayRate.text = ""})


        todayRate.text = "Pobieranie danych"
        yesterdayRate.text = ""
        queue.add(historicRatesRequest)
    }

    private fun showData() {
        todayRate.text = getString(R.string.todayRate, data.last().second)
        yesterdayRate.text = getString(R.string.yesterdayRate, data[data.size-2].second)

        val entries = ArrayList<Entry>()
        for((index, element) in data.withIndex()){
            entries.add(Entry(index.toFloat(), element.second.toFloat()))
        }

        val lineData = LineData(LineDataSet(entries, "Kurs $currencyCode"))

        lineChart.data = lineData
        val description = Description()
        description.text = "Kurs %s z ostatnich 30 dni".format(currencyCode)
        lineChart.description = description
        lineChart.xAxis.valueFormatter = IndexAxisValueFormatter(data.map { it.first }.toTypedArray())
        lineChart.invalidate()

    }

    private fun showWeekData() {
        val entries = ArrayList<Entry>()
        for((index, element) in data.withIndex()){
            if (index > 22) {
                entries.add(Entry(index.toFloat(), element.second.toFloat()))
            }
        }
        val lineWeekData = LineData(LineDataSet(entries, "Kurs $currencyCode"))

        weekLineChart.data = lineWeekData
        val description = Description()
        description.text = "Kurs %s z ostatnich 7 dni".format(currencyCode)
        weekLineChart.description = description
        weekLineChart.xAxis.valueFormatter = IndexAxisValueFormatter(data.map { it.first }.toTypedArray())
        weekLineChart.invalidate()
    }

    private fun loadHistoricData(response: JSONObject?) {
        response?.let {
            val rates = response.getJSONArray("rates")
            val ratesCount = rates.length()
            val tmpData = arrayOfNulls<Pair<String,Double>>(ratesCount)

            for(i in 0 until ratesCount){
                val date = rates.getJSONObject(i).getString("effectiveDate")
                val rate = rates.getJSONObject(i).getDouble("mid")

                tmpData[i] = Pair(date, rate)
            }
            data = tmpData as Array<Pair<String, Double>>
        }
    }

}