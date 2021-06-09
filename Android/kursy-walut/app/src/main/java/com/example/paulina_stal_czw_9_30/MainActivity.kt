package com.example.paulina_stal_czw_9_30

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import org.json.JSONArray
import androidx.recyclerview.widget.ConcatAdapter


class MainActivity : AppCompatActivity() {
    private lateinit var currencyList: RecyclerView
    private lateinit var adapterA: CurrencyListAdapter
    private lateinit var adapterB: CurrencyListAdapter
    private lateinit var concatAdapter: ConcatAdapter
    // private lateinit var dataSet: Array<CurrencyDetails>

    private val urlA = "http://api.nbp.pl/api/exchangerates/tables/A/last/2?format=jason"
    private val urlB = "http://api.nbp.pl/api/exchangerates/tables/B/last/2?format=jason"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "Kurs Walut"
        DataHolder.prepare(applicationContext)

        currencyList = findViewById(R.id.currencyList)
        currencyList.layoutManager = LinearLayoutManager(this)
        adapterA = CurrencyListAdapter(emptyArray(), this)
        adapterB = CurrencyListAdapter(emptyArray(), this)

        makeRequest(urlA, adapterA)
        makeRequest(urlB, adapterB)
        concatAdapter = ConcatAdapter(adapterA, adapterB)
        currencyList.adapter = concatAdapter

    }

    private fun makeRequest(url: String, adapter: CurrencyListAdapter){
        val queue = DataHolder.queue

        val currentRateRequest = JsonArrayRequest(Request.Method.GET, url, null,
                { response ->
                    println("Success!")
                    adapter.dataSet = loadData(response)
                    adapter.notifyDataSetChanged()
                },
                { error ->
                    val text = "Wystąpił problem z pobraniem danych"
                    val duration = Toast.LENGTH_SHORT
                    val toast = Toast.makeText(applicationContext, text, duration)
                    toast.show()
                    Log.d("rates:fetchError", error.toString())
                })

        queue.add(currentRateRequest)
    }

    private fun loadData(response: JSONArray?): Array<CurrencyDetails> {
        response?.let {
            val rates = response.getJSONObject(1).getJSONArray("rates")
            val yesterdayRates = response.getJSONObject(0).getJSONArray("rates")
            val tableName = response.getJSONObject(1).getString("table")
            val ratesCount = rates.length()
            val tmpData = arrayOfNulls<CurrencyDetails>(ratesCount)

            for(i in 0 until ratesCount){
                val currencyCode = rates.getJSONObject(i).getString("code")
                val rate = rates.getJSONObject(i).getDouble("mid")
                val yesterdayRate = yesterdayRates.getJSONObject(i).getDouble("mid")
                val flag = DataHolder.getFlag(currencyCode)
                val currencyObject = CurrencyDetails(tableName, currencyCode, rate, yesterdayRate, flag)
                tmpData[i] = currencyObject
            }
            return tmpData as Array<CurrencyDetails>
        }
        return emptyArray()

    }
}