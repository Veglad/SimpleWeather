package com.example.vshcheglov.simpleweather.data.server

import com.example.vshcheglov.simpleweather.data.database.ForecastDb
import com.example.vshcheglov.simpleweather.domain.datasource.ForecastDataSource
import com.example.vshcheglov.simpleweather.domain.model.Forecast
import com.example.vshcheglov.simpleweather.domain.model.ForecastList
import com.example.vshcheglov.simpleweather.mappers.ServerDataMapper

class ForecastServer(private val dataMapper: ServerDataMapper = ServerDataMapper(),
                     private val forecastDatabase: ForecastDb = ForecastDb()) : ForecastDataSource {

    override fun requestForecastByZipCode(zipCode: Long, date: Long): ForecastList? {
        val result = ForecastByZipCodeRequest(zipCode).execute()
        val converted = dataMapper.convertToDomain(zipCode, result)
        forecastDatabase.saveForecast(converted)
        return forecastDatabase.requestForecastByZipCode(zipCode, date)
    }

    override fun requestDayForecast(id: Long): Forecast? {
        throw UnsupportedOperationException()
    }
}