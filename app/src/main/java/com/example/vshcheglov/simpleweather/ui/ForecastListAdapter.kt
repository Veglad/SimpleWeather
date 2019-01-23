package com.example.vshcheglov.simpleweather.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.vshcheglov.simpleweather.R
import com.example.vshcheglov.simpleweather.domain.model.Forecast
import com.example.vshcheglov.simpleweather.domain.model.ForecastList
import com.squareup.picasso.Picasso
import org.jetbrains.anko.find

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

    class ViewHolder(view: View, private val itemClick: (Forecast) -> Unit) :
        RecyclerView.ViewHolder(view) {

        private val iconView = view.find<ImageView>(R.id.itemForecastIcon)
        private val dateView = view.find<TextView>(R.id.itemForecastDateTextView)
        private val descriptionView = view.find<TextView>(R.id.descriptionTextView)
        private val maxTemperatureView = view.find<TextView>(R.id.itemForecastMaxTemperatureTextView)
        private val minTemperatureView = view.find<TextView>(R.id.itemForecastMinTemperatureTextView)

        fun bindForecast(forecast: Forecast) {
            with(forecast) {
                Picasso.get().load(iconUrl).into(iconView)
                dateView.text = date
                descriptionView.text = description
                maxTemperatureView.text = high.toString()
                minTemperatureView.text = low.toString()
                itemView.setOnClickListener{itemClick(forecast)}
            }
        }
    }
}