package com.example.vshcheglov.simpleweather.mappers

import com.example.vshcheglov.simpleweather.data.server.Forecast
import com.example.vshcheglov.simpleweather.data.server.ForecastResult
import com.example.vshcheglov.simpleweather.domain.model.ForecastList
import java.text.DateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import com.example.vshcheglov.simpleweather.domain.model.Forecast as ModelForecast

class ServerDataMapper {

    fun convertToDomain(zipCode: Long, forecast: ForecastResult): ForecastList =
        ForecastList(zipCode, forecast.city.name, forecast.city.country, convertForecastListToDomain(forecast.list))

    private fun convertForecastListToDomain(list: List<Forecast>): List<ModelForecast> {
        return list.mapIndexed { i, forecast ->
            val dateInMillis = Calendar.getInstance().timeInMillis + TimeUnit.DAYS.toMillis(i.toLong())
            convertForecastItemToDomain(forecast.copy(dt = dateInMillis))
        }
    }

    private fun convertForecastItemToDomain(forecast: Forecast): ModelForecast {
        return ModelForecast(-1, forecast.dt, forecast.weather[0].description,
            forecast.temp.max.toInt(), forecast.temp.min.toInt(), generateIconUrl(forecast.weather[0].icon))
    }

    private fun generateIconUrl(iconCode: String) = "http://openweathermap.org/img/w/$iconCode.png"
}