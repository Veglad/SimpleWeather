package com.example.vshcheglov.simpleweather.domain.commands

import com.example.vshcheglov.simpleweather.domain.datasource.ForecastProvider
import com.example.vshcheglov.simpleweather.domain.model.Forecast

class RequestDayForecastCommand(
    val id: Long,
    private val forecastProvider: ForecastProvider = ForecastProvider()) :
    Command<Forecast>  {

    override fun execute(): Forecast = forecastProvider.requestForecast(id)
}