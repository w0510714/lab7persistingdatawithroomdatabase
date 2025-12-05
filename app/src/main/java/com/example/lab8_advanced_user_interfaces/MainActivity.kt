package com.example.lab8_advanced_user_interfaces

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.lab8_advanced_user_interfaces.models.ForecastResponse
import com.example.lab8_advanced_user_interfaces.persistence.AppDatabase
import com.example.lab8_advanced_user_interfaces.persistence.entities.EntityModelConverter
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var locationEditText: EditText
    private lateinit var predictButton: Button
    private lateinit var forecastDetailsTextView: TextView

    private val converter = EntityModelConverter()

    private val predictionActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        loadForecastHistory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nameEditText = findViewById(R.id.activity_main_enter_name)
        locationEditText = findViewById(R.id.activity_main_enter_location)
        predictButton = findViewById(R.id.activity_main_predict_button)
        forecastDetailsTextView = findViewById(R.id.forecast_details_textview)

        predictButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val location = locationEditText.text.toString()
            val intent = PredictionActivity.newIntent(this, name, location)
            predictionActivityLauncher.launch(intent)
        }

        loadForecastHistory()
    }

    private fun loadForecastHistory() {
        lifecycleScope.launch {
            val database = AppDatabase.getDatabase(this@MainActivity)
            val forecasts = database.forecastDao().getAll()
            val forecastModels = forecasts.map { converter.toModel(it) }
            updateUi(forecastModels)
        }
    }

    private fun updateUi(forecasts: List<ForecastResponse>) {
        val details = StringBuilder()
        for ((index, forecast) in forecasts.withIndex()) {
            details.append("Location: ${forecast.location.name}, ${forecast.location.region}, ${forecast.location.country}\n")
            details.append("Temperature: ${forecast.current.tempC}°C / ${forecast.current.tempF}°F\n")
            details.append("Wind: ${forecast.current.windKph} kph from the ${forecast.current.windDir}\n")
            details.append("Last updated: ${forecast.current.lastUpdated}")
            if (index < forecasts.size - 1) {
                details.append("\n\n---\n\n")
            }
        }
        forecastDetailsTextView.text = details.toString()
    }
}
