package com.example.lab7_persisting_data_with_room_database.persistence.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "forecasts")
data class ForecastResponse(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @Embedded
    val location: Location,
    @Embedded
    val current: Current
)
