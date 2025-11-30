package com.example.lab7_persisting_data_with_room_database

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import com.example.lab7_persisting_data_with_room_database.api.WeatherRetrofitApi
import com.example.lab7_persisting_data_with_room_database.persistence.AppDatabase
import com.example.lab7_persisting_data_with_room_database.persistence.entities.EntityModelConverter
import kotlinx.coroutines.launch

class PredictionActivity : AppCompatActivity() {

    private lateinit var weatherMessageTextView: TextView
    private lateinit var subheadingTextView: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var finishButton: Button
    private val api = WeatherRetrofitApi()
    private val converter = EntityModelConverter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.prediction_activity)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        weatherMessageTextView = findViewById(R.id.prediction_weather_message_textview)
        subheadingTextView = findViewById(R.id.subheading_textview)
        progressBar = findViewById(R.id.progressBar)
        finishButton = findViewById(R.id.prediction_activity_finish_button)

        val name = intent.getStringExtra(EXTRA_NAME)
        val location = intent.getStringExtra(EXTRA_LOCATION)

        subheadingTextView.text = if (!name.isNullOrEmpty()) "$name, I predict..." else "I predict..."

        if (!location.isNullOrEmpty()) {
            loadWeatherDataAndSave(location)
        } else {
            weatherMessageTextView.text = "No location provided."
            progressBar.visibility = View.GONE
            finishButton.isEnabled = true
        }

        finishButton.setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }
    }

    private fun loadWeatherDataAndSave(location: String) {
        progressBar.visibility = View.VISIBLE
        finishButton.isEnabled = false

        lifecycleScope.launch {
            try {
                val forecastResponse = api.getForecast(location, 1)
                val forecastEntity = converter.toEntity(forecastResponse)
                AppDatabase.getDatabase(this@PredictionActivity).forecastDao().insert(forecastEntity)

                val temp = forecastResponse.current.tempC
                val windSpeed = forecastResponse.current.windKph
                val windDir = forecastResponse.current.windDir

                val weatherMessage = "In $location, the temperature is ${temp}°C, with winds from the $windDir at $windSpeed km/h. \n\nData saved to database."
                weatherMessageTextView.text = weatherMessage

            } catch (e: Exception) {
                weatherMessageTextView.text = "Error loading weather data."
                Toast.makeText(
                    this@PredictionActivity,
                    "Error loading or saving weather data: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            } finally {
                progressBar.visibility = View.GONE
                finishButton.isEnabled = true
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                setResult(RESULT_OK)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        private const val EXTRA_NAME = "com.example.lab7_persisting_data_with_room_database.EXTRA_NAME"
        private const val EXTRA_LOCATION = "com.example.lab7_persisting_data_with_room_database.EXTRA_LOCATION"

        fun newIntent(context: Context, name: String?, location: String?): Intent {
            return Intent(context, PredictionActivity::class.java).apply {
                putExtra(EXTRA_NAME, name)
                putExtra(EXTRA_LOCATION, location)
            }
        }
    }
}
