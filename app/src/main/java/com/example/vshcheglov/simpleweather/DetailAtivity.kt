package com.example.vshcheglov.simpleweather

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.widget.TextView
import com.example.vshcheglov.simpleweather.domain.commands.RequestDayForecastCommand
import com.example.vshcheglov.simpleweather.domain.model.Forecast
import com.example.vshcheglov.simpleweather.extensions.toDateString
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_ativity.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class DetailAtivity : AppCompatActivity() {

    companion object {
        val ID = "DetailActivity:id"
        val CITY_NAME = "DetailActivity:cityName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_ativity)

        detailCity.text = intent.getStringExtra(CITY_NAME)

        doAsync {
            val forecastId = intent.getLongExtra(ID, -1)
            val result = RequestDayForecastCommand(forecastId).execute()
            uiThread { bindForecast(result) }
        }
    }

    private fun bindForecast(forecast: Forecast) {
        Picasso.get().load(forecast.iconUrl).into(detailImage)
        detailDate.text = forecast.date.toDateString()
        detailDescription.text = forecast.description
        bindTemperature(forecast.high, detailMaxTemperature)
        bindTemperature(forecast.low, detailMinTemperature)
    }

    private fun bindTemperature(temperature: Int, textView: TextView) {
        textView.text = temperature.toString()
        val color = when{
            temperature < 0 -> android.R.color.holo_blue_dark
            temperature in 0..23 -> android.R.color.holo_green_dark
            else -> android.R.color.holo_red_dark
        }
        textView.setTextColor(ContextCompat.getColor(this, color))
    }
}
