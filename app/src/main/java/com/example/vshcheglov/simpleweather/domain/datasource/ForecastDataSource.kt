package com.example.vshcheglov.simpleweather.domain.datasource

import com.example.vshcheglov.simpleweather.domain.model.ForecastList

interface ForecastDataSource {
    fun requestForecastByZipCode(zipCode: Long, data: Long): ForecastList?
}