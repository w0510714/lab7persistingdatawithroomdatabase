package com.example.lab7_persisting_data_with_room_database.models

import com.google.gson.annotations.SerializedName

data class Current(
    @SerializedName("last_updated") val lastUpdated: String,
    @SerializedName("temp_c") val tempC: Double,
    @SerializedName("temp_f") val tempF: Double,
    @SerializedName("wind_mph") val windMph: Double,
    @SerializedName("wind_kph") val windKph: Double,
    @SerializedName("wind_dir") val windDir: String
)
