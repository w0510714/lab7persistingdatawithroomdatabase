package com.example.lab7_persisting_data_with_room_database.models

data class ForecastResponse(
    val location: Location,
    val current: Current
)
