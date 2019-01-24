package com.example.vshcheglov.simpleweather.data.database

import com.example.vshcheglov.simpleweather.extensions.parseList
import com.example.vshcheglov.simpleweather.extensions.parseOpt
import org.jetbrains.anko.db.select
import java.util.HashMap

class ForecastDbQueries(
    val forecastDbHelper: ForecastDbHelper = ForecastDbHelper.instance,
    val dataMapper: DbDataMapper = DbDataMapper()) {

    fun requestForecastByZipCode(zipCode: Long, date: Long) = forecastDbHelper.use {
        val dailyRequest = "${DayForecastTable.CITY_ID} = ? " +
                "AND ${DayForecastTable.DATE} >= ?"

        val dailyForecastList = select(DayForecastTable.NAME)
            .whereSimple(dailyRequest, zipCode.toString(), date.toString())
            .parseList { DayForecast(HashMap(it)) }

        val city = select(CityForecastTable.NAME)
            .whereSimple("${CityForecastTable.ID} = ?", zipCode.toString())
            .parseOpt { CityForecast(HashMap(it), dailyForecastList) }

        city?.let { dataMapper.convertToDomain(city) }
    }
}