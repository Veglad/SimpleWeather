package com.example.vshcheglov.simpleweather.data.database

import com.example.vshcheglov.simpleweather.domain.datasource.ForecastDataSource
import com.example.vshcheglov.simpleweather.domain.model.ForecastList
import com.example.vshcheglov.simpleweather.extensions.clear
import com.example.vshcheglov.simpleweather.extensions.parseList
import com.example.vshcheglov.simpleweather.extensions.parseOpt
import com.example.vshcheglov.simpleweather.extensions.toVarargArray
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import java.util.HashMap

class ForecastDb(
    val forecastDbHelper: ForecastDbHelper = ForecastDbHelper.instance,
    val dataMapper: DbDataMapper = DbDataMapper()) : ForecastDataSource {

    override fun requestForecastByZipCode(zipCode: Long, date: Long) = forecastDbHelper.use {
        val dailyRequest = "${DayForecastTable.CITY_ID} = ? " +
                "AND ${DayForecastTable.DATE} >= ?"

        val dailyForecastList = select(DayForecastTable.NAME)
            .whereSimple(dailyRequest, zipCode.toString(), date.toString())
            .parseList { DayForecast(HashMap(it)) }

        val city = select(CityForecastTable.NAME)
            .whereSimple("${CityForecastTable.ID} = ?", zipCode.toString())
            .parseOpt { CityForecast(HashMap(it), dailyForecastList) }

        city?.let { dataMapper.convertForecastToDomain(city) }
    }

    fun saveForecast(forecast: ForecastList) = forecastDbHelper.use {
        //clear tables because we load new data
        clear(CityForecastTable.NAME)
        clear(CityForecastTable.NAME)

        with(dataMapper.convertForecastFromDomain(forecast)) {
            insert(CityForecastTable.NAME, *map.toVarargArray())
            dailyForecast.forEach { insert(DayForecastTable.NAME, *it.map.toVarargArray()) }
        }
    }
}