package com.example.vshcheglov.simpleweather.data.database

import com.example.vshcheglov.simpleweather.domain.model.Forecast
import com.example.vshcheglov.simpleweather.domain.model.ForecastList

open class DbDataMapper {
    open fun convertForecastToDomain(forecast: CityForecast) = with(forecast) {
        val forecastList = dailyForecast.map { convertDayForecastToDomain(it) }
        ForecastList(_id, city, country, forecastList)
    }

    fun convertDayForecastToDomain(dayForecast: DayForecast) = with(dayForecast) {
        Forecast(_id, date, description, high, low, iconUrl)
    }

    fun convertForecastFromDomain(forecast: ForecastList) = with(forecast) {
        val dailyForecastList = dailyForecast.map { convertDayFromDomain(id, it) }
        CityForecast(id, city, country, dailyForecastList)
    }

    private fun convertDayFromDomain(cityId: Long, forecast: Forecast) = with(forecast) {
        DayForecast(date, description, high, low, iconUrl, cityId)
    }
}

