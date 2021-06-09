package com.example.paulina_stal_czw_9_30

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray

class ExchangeRate : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    lateinit var zlotyEditText: EditText
    lateinit var currencyEditText: EditText
    lateinit var spinner: Spinner
    private lateinit var data: Array<Pair<String, Double>?>
    private lateinit var currencies: Array<Pair<String, Double>?>
    lateinit var nameCur: Array<String?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exchange_rate)
        title = "Przelicznik"

        spinner = findViewById<Spinner>(R.id.currencySpinner)
        zlotyEditText = findViewById(R.id.zlotyEditText)
        currencyEditText = findViewById(R.id.currencyEditText)


        // Take the instance of Spinner and
        // apply OnItemSelectedListener on it which
        // tells which item of spinner is clicked
        spinner.onItemSelectedListener = this

        nameCur = arrayOfNulls<String>(35)
        currencies = getHistoricRates()

        for(i in currencies.indices){
            nameCur[i] = currencies[i]?.first.toString()
        }

    }

    private fun test() {
        var text: String = "abc"
        if(spinner.selectedItem != null){
            text = spinner.selectedItem.toString()
        }
        println(text)
        println(currencies[0]?.second)
    }

    private fun getHistoricRates(): Array<Pair<String, Double>?> {
        val queue = Volley.newRequestQueue(this)
        //var currencies = arrayOfNulls<String>(35)
        var currencies = arrayOfNulls<Pair<String, Double>>(35)
        var tmp = arrayOfNulls<String>(35)

        val url = "http://api.nbp.pl/api/exchangerates/tables/A?format=json"

        val currentRateRequest = JsonArrayRequest(Request.Method.GET, url, null,
                { response ->
                    println("Success!")
                    currencies = loadHistoricData(response)

                    for (i in currencies.indices) {
                        tmp[i] = currencies[i]?.first
                    }

                    val ad: ArrayAdapter<*> = ArrayAdapter<Any?>(
                            this,
                            android.R.layout.simple_spinner_item, tmp)
                    //loadHistoricData
                    // set simple layout resource file
                    // for each item of spinner
                    ad.setDropDownViewResource(
                            android.R.layout.simple_spinner_dropdown_item)

                    // Set the ArrayAdapter (ad) data on the
                    // Spinner which binds data to spinner
                    spinner.adapter = ad


                },
                { error ->
                    val text = "Wystąpił problem z pobraniem danych"
                    val duration = Toast.LENGTH_SHORT
                    val toast = Toast.makeText(applicationContext, text, duration)
                    toast.show()
                    Log.d("rates:fetchError", error.toString())
                })

        queue.add(currentRateRequest)
        return currencies
    }

    private fun loadHistoricData(response: JSONArray?): Array<Pair<String, Double>?> {
        response?.let {
            val rates = response.getJSONObject(0).getJSONArray("rates")
            val ratesCount = rates.length()
            val tmpData = arrayOfNulls<Pair<String, Double>>(ratesCount)
            //val currencies = arrayOfNulls<String>(ratesCount)

            for(i in 0 until ratesCount){
                val currencyCode = rates.getJSONObject(i).getString("code")
                val rate = rates.getJSONObject(i).getDouble("mid")

                tmpData[i] = Pair(currencyCode, rate)
                //currencies[i] = currencyCode
            }
            data = tmpData as Array<Pair<String, Double>?>
            //println(data[0].first)
            //return currencies
            return data
        }
        return emptyArray()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        // make toastof name of currency
        // which is selected in spinner
        Toast.makeText(applicationContext,
                nameCur[position],
                Toast.LENGTH_LONG)
                //.show()
        if (parent != null) {
            println(parent.getItemAtPosition(position))
        }

        test()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}