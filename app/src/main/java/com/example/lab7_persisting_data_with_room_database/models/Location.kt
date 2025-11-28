package com.example.lab7_persisting_data_with_room_database.models

data class Location(
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    val localtime: String
)
