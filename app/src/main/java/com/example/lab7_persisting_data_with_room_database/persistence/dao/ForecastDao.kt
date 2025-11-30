package com.example.lab7_persisting_data_with_room_database.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.lab7_persisting_data_with_room_database.persistence.entities.ForecastResponse

@Dao
interface ForecastDao {
    @Insert
    suspend fun insert(forecast: ForecastResponse)

    @Query("SELECT * FROM forecasts ORDER BY id DESC LIMIT 1")
    suspend fun getLatest(): ForecastResponse?

    @Query("SELECT * FROM forecasts ORDER BY id DESC")
    suspend fun getAll(): List<ForecastResponse>
}
