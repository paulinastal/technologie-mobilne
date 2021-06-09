package com.example.paulina_stal_czw_9_30

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley.newRequestQueue
import com.blongho.country_data.Country
import com.blongho.country_data.World

object DataHolder {
    lateinit var queue: RequestQueue
    private lateinit var countries : List<Country>

    fun prepare(context: Context){
        queue = newRequestQueue(context)
        World.init(context)
        countries = World.getAllCountries().distinctBy { it.currency.code }

    }

    fun getFlag(countryCode: String): Int{
        //val flagID = countries.find { it.currency.code == countryCode }?.flagResource ?:World.getWorldFlag()

        return when (countryCode) {
            "EUR" ->  R.drawable.eu
            "GBP" -> R.drawable.gb
            "CHF" -> R.drawable.ch
            "HKD" -> R.drawable.hk
            "USD" -> R.drawable.us
            else -> countries.find { it.currency.code == countryCode }?.flagResource
                ?: World.getWorldFlag()
        }
    }
}