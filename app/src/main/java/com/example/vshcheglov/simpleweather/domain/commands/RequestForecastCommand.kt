package com.example.vshcheglov.simpleweather.domain.commands

import com.example.vshcheglov.simpleweather.domain.datasource.ForecastProvider
import com.example.vshcheglov.simpleweather.domain.model.ForecastList

class RequestForecastCommand(val zipCode: Long,
                             val forecastProvider: ForecastProvider = ForecastProvider()
) :
    Command<ForecastList> {

    companion object {
        val DAYS = 7
    }

    override fun execute(): ForecastList {
        return forecastProvider.requestByZipCode(zipCode, DAYS)
    }
}