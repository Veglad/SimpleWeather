package com.example.vshcheglov.simpleweather.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vshcheglov.simpleweather.R
import com.example.vshcheglov.simpleweather.domain.model.Forecast
import com.example.vshcheglov.simpleweather.domain.model.ForecastList
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_forecast.view.*

class ForecastListAdapter(private val weekForecast: ForecastList,
                          private val itemClick: (Forecast) -> Unit) : RecyclerView.Adapter<ForecastListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_forecast, parent, false)

        return ViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindForecast(weekForecast[position])
    }

    override fun getItemCount(): Int = weekForecast.size

    class ViewHolder(val view: View, private val itemClick: (Forecast) -> Unit) :
        RecyclerView.ViewHolder(view) {


        fun bindForecast(forecast: Forecast) {
            with(forecast) {
                Picasso.get().load(iconUrl).into(view.itemForecastIcon)
                view.dateTextView.text = date
                view.descriptionTextView.text = description
                view.maxTemperatureTextView.text = high.toString()
                view.minTemperatureTextView.text = low.toString()
                itemView.setOnClickListener{itemClick(forecast)}
            }
        }
    }
}