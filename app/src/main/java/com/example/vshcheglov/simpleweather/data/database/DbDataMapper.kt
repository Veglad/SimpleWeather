package com.example.vshcheglov.simpleweather.data.database

import com.example.vshcheglov.simpleweather.domain.model.Forecast
import com.example.vshcheglov.simpleweather.domain.model.ForecastList

open class DbDataMapper {
    open fun convertToDomain(forecast: CityForecast) = with(forecast) {
        val forecastList = dailyForecast.map { convertDayForecastToDomain(it) }
        ForecastList(_id, city, coutnry, forecastList)
    }

    private fun convertDayForecastToDomain(dayForecast: DayForecast) = with(dayForecast) {
        Forecast(date, description, high, low, iconUrl)
    }
}