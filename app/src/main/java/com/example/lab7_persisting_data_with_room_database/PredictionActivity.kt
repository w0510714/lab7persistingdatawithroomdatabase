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
import com.example.lab7_persisting_data_with_room_database.api.WeatherRetrofitApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PredictionActivity : AppCompatActivity() {

    private lateinit var weatherMessageTextView: TextView
    private lateinit var subheadingTextView: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var finishButton: Button
    private val api = WeatherRetrofitApi()

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
        subheadingTextView.text = if (!name.isNullOrEmpty()) "$name, I predict..." else "I predict..."

        loadWeatherData()

        finishButton.setOnClickListener {
            finish()
        }
    }

    private fun loadWeatherData() {
        // Show progress bar and disable button
        progressBar.visibility = View.VISIBLE
        finishButton.isEnabled = false

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val forecastResponse = api.getForecast("Halifax", 1)
                val location = forecastResponse.location.name
                val temp = forecastResponse.current.tempC
                val windSpeed = forecastResponse.current.windKph
                val windDir = forecastResponse.current.windDir

                val weatherMessage = "In $location, the temperature is ${temp}°C, with winds from the $windDir at $windSpeed km/h."
                weatherMessageTextView.text = weatherMessage

            } catch (e: Exception) {
                weatherMessageTextView.text = "Error loading weather data."
                Toast.makeText(
                        this@PredictionActivity,
                "Error loading weather data: ${e.message}",
                        Toast.LENGTH_LONG
                ).show()
            } finally {
                // Hide progress bar and re-enable button
                progressBar.visibility = View.GONE
                finishButton.isEnabled = true
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
        private const val EXTRA_NAME = "com.example.lab5_multi_screen_applications.EXTRA_NAME"

        fun newIntent(context: Context, name: String?): Intent {
            return Intent(context, PredictionActivity::class.java).apply {
                putExtra(EXTRA_NAME, name)
            }
        }
    }
}
