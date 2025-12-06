package com.example.lab8_advanced_user_interfaces

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lab8_advanced_user_interfaces.persistence.entities.ForecastResponse

class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val locationTextView: TextView = itemView.findViewById(R.id.locationTextView)
    private val temperatureTextView: TextView = itemView.findViewById(R.id.temperatureTextView)
    private val conditionTextView: TextView = itemView.findViewById(R.id.conditionTextView)

    fun bind(forecast: ForecastResponse) {
        locationTextView.text = forecast.location.name
        temperatureTextView.text = "${forecast.current.tempC}°C"
        conditionTextView.text = "Wind: ${forecast.current.windKph} kph"
    }
}