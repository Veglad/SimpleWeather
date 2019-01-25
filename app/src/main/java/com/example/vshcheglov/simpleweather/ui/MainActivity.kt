package com.example.vshcheglov.simpleweather.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.vshcheglov.simpleweather.DetailAtivity
import com.example.vshcheglov.simpleweather.R
import com.example.vshcheglov.simpleweather.domain.commands.RequestForecastCommand
import com.example.vshcheglov.simpleweather.extensions.toDateString
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity() {

    companion object {
        val TEST_CITY_ZIPCODE = 94043L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        forecastListView.layoutManager = LinearLayoutManager(this)

        doAsync {
            val result = RequestForecastCommand(TEST_CITY_ZIPCODE).execute()
            uiThread {
                forecastListView.adapter = ForecastListAdapter(result){
                    val intent = Intent(this@MainActivity, DetailAtivity::class.java)
                    intent.putExtra(DetailAtivity.CITY_NAME, result.city)
                    intent.putExtra(DetailAtivity.ID, it.id)
                    startActivity(intent)
                }
            }
        }
    }
}
