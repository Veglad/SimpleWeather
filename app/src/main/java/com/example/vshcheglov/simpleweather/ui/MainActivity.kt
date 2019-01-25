package com.example.vshcheglov.simpleweather.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.vshcheglov.simpleweather.R
import com.example.vshcheglov.simpleweather.domain.commands.RequestForecastCommand
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity() {

    val TEST_CITY_ZIPCODE = 94043L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        forecastListView.layoutManager = LinearLayoutManager(this)

        doAsync {
            val result = RequestForecastCommand(TEST_CITY_ZIPCODE).execute()
            uiThread {
                val adapter = ForecastListAdapter(result){ toast(it.description) }
                forecastListView.adapter = adapter
            }
        }
    }
}
