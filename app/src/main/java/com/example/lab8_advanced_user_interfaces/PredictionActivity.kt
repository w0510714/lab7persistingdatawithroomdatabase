package com.example.lab8_advanced_user_interfaces

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab8_advanced_user_interfaces.api.WeatherRetrofitApi
import com.example.lab8_advanced_user_interfaces.persistence.AppDatabase
import com.example.lab8_advanced_user_interfaces.persistence.entities.EntityModelConverter
import kotlinx.coroutines.launch
import retrofit2.HttpException

class PredictionActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ForecastAdapter
    private val api = WeatherRetrofitApi()
    private val converter = EntityModelConverter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.prediction_activity)

        val toolbar = findViewById<Toolbar>(R.id.activity_prediction_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        progressBar = findViewById(R.id.activity_prediction_progressBar)

        recyclerView = findViewById(R.id.activity_prediction_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ForecastAdapter()
        recyclerView.adapter = adapter

        val location = intent.getStringExtra(EXTRA_LOCATION)

        if (!location.isNullOrEmpty()) {
            loadWeatherDataAndSave(location)
        } else {
            loadDataFromDatabase()
        }
    }

    private fun loadDataFromDatabase() {
        progressBar.visibility = View.VISIBLE
        lifecycleScope.launch {
            try {
                val forecasts = AppDatabase.getDatabase(this@PredictionActivity).forecastDao().getAll()
                adapter.updateData(forecasts)
            } catch (e: Exception) {
                Toast.makeText(this@PredictionActivity, "Error loading data from database", Toast.LENGTH_SHORT).show()
            } finally {
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun loadWeatherDataAndSave(location: String) {
        progressBar.visibility = View.VISIBLE
        lifecycleScope.launch {
            try {
                val forecastResponse = api.getForecast(location, 1)
                val forecastEntity = converter.toEntity(forecastResponse)
                val forecastDao = AppDatabase.getDatabase(this@PredictionActivity).forecastDao()
                forecastDao.insert(forecastEntity)
                val forecasts = forecastDao.getAll() // Re-fetch data in the same coroutine
                adapter.updateData(forecasts) // Update adapter directly
            } catch (e: HttpException) {
                if (e.code() == 400) {
                    Toast.makeText(this@PredictionActivity, "That location does not exist.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@PredictionActivity, "Error loading weather data: ${e.message()}", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@PredictionActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            } finally {
                progressBar.visibility = View.GONE
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        private const val EXTRA_LOCATION = "com.example.lab8_advanced_user_interfaces.EXTRA_LOCATION"

        fun newIntent(context: Context, location: String?): Intent {
            return Intent(context, PredictionActivity::class.java).apply {
                putExtra(EXTRA_LOCATION, location)
            }
        }
    }
}