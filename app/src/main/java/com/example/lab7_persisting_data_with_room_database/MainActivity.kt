package com.example.lab7_persisting_data_with_room_database

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.lab7_persisting_data_with_room_database.api.WeatherRetrofitApi
import com.example.lab7_persisting_data_with_room_database.persistence.AppDatabase
import com.example.lab7_persisting_data_with_room_database.persistence.entities.EntityModelConverter
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var locationEditText: EditText
    private lateinit var searchButton: Button
    private lateinit var resultTextView: TextView

    private val converter = EntityModelConverter()
    private val weatherApi = WeatherRetrofitApi()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        locationEditText = findViewById(R.id.location_edit_text)
        searchButton = findViewById(R.id.search_button)
        resultTextView = findViewById(R.id.result_text_view)

        searchButton.setOnClickListener {
            val location = locationEditText.text.toString()
            if (location.isNotEmpty()) {
                getForecast(location)
            }
        }
    }

    private fun getForecast(location: String) {
        lifecycleScope.launch {
            try {
                val forecastResponse = weatherApi.getForecast(location, 1)
                val forecastEntity = converter.toEntity(forecastResponse)

                val database = AppDatabase.getDatabase(this@MainActivity)
                database.forecastDao().insert(forecastEntity)

                resultTextView.text = "Forecast for $location saved to database."
            } catch (e: Exception) {
                resultTextView.text = "Error: ${e.message}"
            }
        }
    }
}
