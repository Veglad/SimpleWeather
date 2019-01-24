package com.example.vshcheglov.simpleweather.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.vshcheglov.simpleweather.ui.App
import org.jetbrains.anko.db.*

class ForecastDbHelper(context: Context = App.instance) : ManagedSQLiteOpenHelper(context,
    ForecastDbHelper.DB_NAME, null, ForecastDbHelper.DB_VERSION) {

    companion object {
        val DB_NAME = "forecast.db"
        val DB_VERSION = 1
        val instance by lazy { ForecastDbHelper() }
    }

    override fun onCreate(database: SQLiteDatabase?) {
        database?.createTable(CityForecastTable.NAME, true,
            CityForecastTable.ID to INTEGER + PRIMARY_KEY,
            CityForecastTable.CITY to TEXT,
            CityForecastTable.COUNTRY to TEXT
        )

        database?.createTable(DayForecastTable.NAME, true,
            DayForecastTable.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            DayForecastTable.DATE to INTEGER,
            DayForecastTable.DESCRIPTION to TEXT,
            DayForecastTable.HIGH to INTEGER,
            DayForecastTable.LOW to INTEGER,
            DayForecastTable.ICON_URL to TEXT,
            DayForecastTable.CITY_ID to INTEGER)
    }

    override fun onUpgrade(database: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        database?.dropTable(CityForecastTable.NAME, true)
        database?.dropTable(DayForecastTable.NAME, true)
        onCreate(database)
    }
}