package com.example.paulina_stal_czw_9_30

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CurrencyListAdapter(var dataSet: Array<CurrencyDetails>, val context: Context) : RecyclerView.Adapter<CurrencyListAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val currencyText : TextView
        val currencyRate : TextView
        val currencyFlag : ImageView
        val rateArrow : ImageView
        init {
            // Define click listener for the ViewHolder's View.
            currencyText = view.findViewById(R.id.currency_text)
            currencyRate = view.findViewById(R.id.currency_rate)
            currencyFlag = view.findViewById(R.id.currency_flag)
            rateArrow = view.findViewById(R.id.rateArrow)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.currency_row, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val currency = dataSet[position]
        viewHolder.currencyText.text = currency.currencyCode
        viewHolder.currencyRate.text = "%.4f PLN".format(currency.rate)
        viewHolder.currencyFlag.setImageResource(currency.flag)
        viewHolder.rateArrow.setImageResource(setArrow(currency.rate,currency.yesterdayRate))
        viewHolder.itemView.setOnClickListener { goToDetails(currency) }
    }

    private fun setArrow(rate: Double, yesterdayRate: Double): Int {
        return if(rate < yesterdayRate) R.drawable.down_arrow else R.drawable.up_arrow
    }

    private fun goToDetails(currency: CurrencyDetails) {
        val intent = Intent(context, HistoricRatesActivity::class.java).apply{
            putExtra("currencyCode", currency.currencyCode)
            putExtra("tableName", currency.tableName)
        }
        context.startActivity(intent)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}

