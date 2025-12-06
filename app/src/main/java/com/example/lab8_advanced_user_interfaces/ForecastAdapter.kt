package com.example.lab8_advanced_user_interfaces

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lab8_advanced_user_interfaces.persistence.entities.ForecastResponse

class ForecastAdapter : RecyclerView.Adapter<ForecastViewHolder>() {

    private val forecasts = mutableListOf<ForecastResponse>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_forecast, parent, false)
        return ForecastViewHolder(view)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val forecast = forecasts[position]
        holder.bind(forecast)
    }

    override fun getItemCount() = forecasts.size

    fun updateData(newForecasts: List<ForecastResponse>) {
        forecasts.clear()
        forecasts.addAll(newForecasts)
        notifyDataSetChanged()
    }
}