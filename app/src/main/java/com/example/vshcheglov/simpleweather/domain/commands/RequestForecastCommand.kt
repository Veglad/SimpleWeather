package com.example.vshcheglov.simpleweather.domain.commands

import com.example.vshcheglov.simpleweather.data.ForecastRequest
import com.example.vshcheglov.simpleweather.domain.model.ForecastList
import com.example.vshcheglov.simpleweather.mappers.ForecastDataMapper

class RequestForecastCommand(val zipCode: String) : Command<ForecastList> {
    override fun execute(): ForecastList {
        val forecastRequest = ForecastRequest(zipCode)
        return  ForecastDataMapper().convertFromDataModel(
            forecastRequest.execute()
        )
    }
}