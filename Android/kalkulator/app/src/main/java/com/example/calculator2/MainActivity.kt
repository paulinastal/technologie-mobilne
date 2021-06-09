package com.example.calculator2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.sqrt


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    var isNew = true
    var dotSec = false

    private fun setNewValue(newValue: String) {
        return textView.setText(newValue)
    }

    fun buttonEvent(view: View) {
        if(isNew){
            setNewValue("")
        }

        isNew = false
        val buttonSelect= view as Button
        var buttonValue:String = textView.text.toString()

        when(buttonSelect.id) {
            zero.id->
            {
                buttonValue += "0"
            }
            one.id->
            {
                buttonValue += "1"
            }
            two.id->
            {
                buttonValue += "2"
            }
            three.id->
            {
                buttonValue += "3"
            }
            four.id->
            {
                buttonValue += "4"
            }
            five.id->
            {
                buttonValue += "5"
            }
            six.id->
            {
                buttonValue += "6"
            }
            seven.id->
            {
                buttonValue += "7"
            }
            eight.id->
            {
                buttonValue += "8"
            }
            nine.id->
            {
                buttonValue += "9"
            }
            dot.id->
            {
                if(!dotSec) {
                    buttonValue += if(buttonValue.isNullOrEmpty()) "0." else "."
                }
                dotSec = true
            }
        }
        setNewValue(buttonValue)
    }

    var operation = "X"
    var oldNumber = ""

    fun operationEvent(view: View)
    {
        val buttonSelected = view as Button
        when(buttonSelected.id)
        {
            multiply.id->
            {
                operation = "X"
            }
            divide.id->
            {
                operation = "÷"
            }
            minus.id->
            {
                operation = "-"
            }
            plus.id->
            {
                operation = "+"
            }
        }
        oldNumber = textView.text.toString()
        isNew = true
        dotSec = false
    }

    fun equalEvent(view: View)
    {
        val newNumber = textView.text.toString()
        var result:Double?=null
        when(operation)
        {
            "X"->
            {
                result = oldNumber.toDouble() * newNumber.toDouble()
                setNewValue(result.toDouble().toString())
            }
            "÷"->
            {
                if(newNumber == "0") {
                    setNewValue("Cannot divide by zero")

                } else{
                    result = oldNumber.toDouble() / newNumber.toDouble()
                    setNewValue("%.5f".format(result))
                }
            }
            "-"->
            {
                result = oldNumber.toDouble() - newNumber.toDouble()
                setNewValue(result.toDouble().toString())
            }
            "+"->
            {
                result = oldNumber.toDouble() + newNumber.toDouble()
                setNewValue(result.toDouble().toString())
            }
        }
        isNew = true
    }

    fun rootEvent(view: View) {
        if(textView.text.toString().toDouble() < 0){
            setNewValue("Invalid input")
        } else {
            val result = sqrt(textView.text.toString().toDouble())
            setNewValue("%.5f".format(result))
        }
        isNew = true
    }

    fun percentEvent(view: View) {
        val result = (textView.text.toString().toDouble())/100
        setNewValue("%.5f".format(result))
        isNew = true
    }

    fun cleanDisplay(view: View) {
        setNewValue("0")
        isNew = true
        dotSec = false
    }

}