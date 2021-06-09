package com.example.paulina_stal_czw_9_30

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GoldRatesActivity : AppCompatActivity() {
    private lateinit var todayGoldRate: TextView
    private lateinit var goldRatesChart: LineChart

    private var rates = mutableListOf<GoldRate>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gold_rates)
        title = "Kurs Złota"

        todayGoldRate = findViewById(R.id.todayGoldRate)
        goldRatesChart = findViewById(R.id.goldRatesChart)

        goldRatesChart.legend.isEnabled = true
        goldRatesChart.description.isEnabled = false
        goldRatesChart.xAxis.labelRotationAngle = -90f
        goldRatesChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        goldRatesChart.extraBottomOffset = 60f

        getHistoricRates()

    }

    inline fun <reified T> Gson.fromJson(json: String) = fromJson<T>(json, object: TypeToken<T>() {}.type)

    data class GoldRate(
            val data: String,
            val cena: Float
    )

    class AxisFormatter(private var rates: MutableList<GoldRate>) : ValueFormatter() {
        override fun getFormattedValue(value: Float): String? {
            return rates.getOrNull(value.toInt())?.data ?: ""
        }
    }

    private fun getHistoricRates() {
        val queue = Volley.newRequestQueue(this)

        val url = "https://api.nbp.pl/api/cenyzlota/last/30?format=json"

        val historicRatesRequest = JsonArrayRequest(Request.Method.GET, url, null,
                { response ->
                    val data = Gson().fromJson<MutableList<GoldRate>>(response.toString())
                    rates = data

                    todayGoldRate.text = "Dzisiejszy kurs:   ${rates[0].cena} PLN / 1 gram"

                    val monthlyDataset = rates.slice(0..29).reversed().mapIndexed { idx, rate ->
                        Entry(
                                idx.toFloat(),
                                rate.cena
                        )
                    }

                    goldRatesChart.data = LineData((LineDataSet(monthlyDataset, "Kurs złota")))
                    val description = Description()
                    description.text = "Kurs złota z ostatnich 30 dni"
                    goldRatesChart.description = description
                    goldRatesChart.xAxis.valueFormatter = AxisFormatter(rates)
                    goldRatesChart.invalidate()

                },
                { error ->
                   todayGoldRate.text = "Wystąpił problem z pobraniem danych"
                   Log.d("rates:fetchError", error.toString())
                }
        )

        todayGoldRate.text = "Pobieranie danych"
        queue.add(historicRatesRequest)
    }

    }
