package com.kosciukvictor.currencyconverter.ui.fragment.rates

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kosciukvictor.currencyconverter.R


class RatesListAdapter(private val rates: List<Double>, private val keys: List<String>) :
    RecyclerView.Adapter<RatesListAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return keys.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(viewGroup.context)
            .inflate(
                R.layout.item_currency,
                viewGroup,
                false
            )

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.tvName.text = keys[position]
        viewHolder.tvRate.text = rates[position].toString()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvRate: TextView = view.findViewById(R.id.tvRate)
    }
}

