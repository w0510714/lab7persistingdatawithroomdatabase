package com.example.lab7_persisting_data_with_room_database.persistence.entities

data class Current(
    val lastUpdated: String,
    val tempC: Double,
    val tempF: Double,
    val windMph: Double,
    val windKph: Double,
    val windDir: String
)
