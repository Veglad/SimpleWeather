package com.example.vshcheglov.simpleweather

import com.example.vshcheglov.simpleweather.R
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.vshcheglov.simpleweather.domain.commands.RequestDayForecastCommand
import com.example.vshcheglov.simpleweather.domain.model.Forecast
import com.example.vshcheglov.simpleweather.extensions.toDateString
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_ativity.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class DetailActivity : AppCompatActivity() {

    companion object {
        val ID = "DetailActivity:id"
        val CITY_NAME = "DetailActivity:cityName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_ativity)

        supportActionBar?.elevation = 0.0f

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
        detailMaxTemperature.text = forecast.high.toString()
        detailMinTemperature.text = forecast.low.toString()
    }
}
