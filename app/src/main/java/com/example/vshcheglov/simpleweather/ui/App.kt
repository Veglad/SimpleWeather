package com.example.vshcheglov.simpleweather.ui

import android.app.Application
import com.example.vshcheglov.simpleweather.extensions.DelegatesExt

class App : Application() {

    companion object {
        var instance: App by DelegatesExt.notNullSingleValue()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}