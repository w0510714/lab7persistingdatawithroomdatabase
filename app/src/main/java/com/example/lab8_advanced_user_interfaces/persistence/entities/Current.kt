package com.example.lab8_advanced_user_interfaces.persistence.entities

data class Current(
    val lastUpdated: String,
    val tempC: Double,
    val tempF: Double,
    val windMph: Double,
    val windKph: Double,
    val windDir: String
)
