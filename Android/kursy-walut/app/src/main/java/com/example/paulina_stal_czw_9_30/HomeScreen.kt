package com.example.paulina_stal_czw_9_30

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class HomeScreen : AppCompatActivity() {
    lateinit var currencyRateButton: Button
    lateinit var goldRateButton: Button
    lateinit var currencyConverterButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        currencyRateButton = findViewById(R.id.currencyRateButton)
        goldRateButton = findViewById(R.id.goldRateButton)
        currencyConverterButton = findViewById(R.id.currencyConverterButton)

        currencyRateButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        goldRateButton.setOnClickListener{
            val intent = Intent(this, GoldRatesActivity::class.java)
            startActivity(intent)
        }

        currencyConverterButton.setOnClickListener{
            val intent = Intent(this, ExchangeRate::class.java)
            startActivity(intent)
        }
    }
}