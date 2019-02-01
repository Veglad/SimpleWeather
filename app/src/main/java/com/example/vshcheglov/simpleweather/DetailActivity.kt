package com.example.vshcheglov.simpleweather

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

        initToolbar()

        detailCityTextView.text = intent.getStringExtra(CITY_NAME)

        doAsync {
            val forecastId = intent.getLongExtra(ID, -1)
            val result = RequestDayForecastCommand(forecastId).execute()
            uiThread { bindForecast(result) }
        }
    }

    private fun initToolbar() {
        setSupportActionBar(detailToolbar)
        supportActionBar?.elevation = 0.0f
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun bindForecast(forecast: Forecast) = with(forecast) {
        Picasso.get().load(iconUrl).into(detailImage)
        detailDateTextView.text = date.toDateString()
        detailDescriptionTextView.text = description
        detailMaxTemperatureTextView.text = high.toString()
        detailMinTemperatureTextView.text = low.toString()
    }
}
