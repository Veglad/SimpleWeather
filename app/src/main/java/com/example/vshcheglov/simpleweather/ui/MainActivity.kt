package com.example.vshcheglov.simpleweather.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.example.vshcheglov.simpleweather.DetailActivity
import com.example.vshcheglov.simpleweather.R
import com.example.vshcheglov.simpleweather.SettingsActivity
import com.example.vshcheglov.simpleweather.domain.commands.RequestForecastCommand
import com.example.vshcheglov.simpleweather.extensions.DelegatesExt
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity() {

    val zipCode: Long by DelegatesExt.longPreference(
        this,
        SettingsActivity.ZIP_CODE,
        SettingsActivity.DEFAULT_ZIP
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        forecastListView.layoutManager = LinearLayoutManager(this)
        initToolbar()

        loadForecast()
    }

    private fun initToolbar() {
        setSupportActionBar(mainToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.title = getString(R.string.app_name)
    }

    private fun loadForecast() {
        doAsync {
            val result = RequestForecastCommand(zipCode).execute()
            uiThread {
                forecastListView.adapter = ForecastListAdapter(result) {
                    val intent = Intent(this@MainActivity, DetailActivity::class.java).apply {
                        putExtra(DetailActivity.CITY_NAME, result.city)
                        putExtra(DetailActivity.ID, it.id)
                    }

                    startActivity(intent)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.mainMenuSettings -> startActivity<SettingsActivity>()
            else -> App.instance.toast("Unknown option")
        }

        return true
    }

    override fun onResume() {
        super.onResume()
        loadForecast()
    }
}
