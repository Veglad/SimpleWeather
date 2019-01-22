package com.example.vshcheglov.simpleweather

import android.util.Log
import java.net.URL

class WeatherRequest(val url: String) {

    fun run(){
        val forecastJsonStr = URL(url).readText()
        Log.d(javaClass.simpleName, forecastJsonStr)
    }
}