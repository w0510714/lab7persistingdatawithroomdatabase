package com.example.lab8_advanced_user_interfaces.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.lab8_advanced_user_interfaces.persistence.entities.ForecastResponse

@Dao
interface ForecastDao {
    @Insert
    suspend fun insert(forecast: ForecastResponse)

    @Query("SELECT * FROM forecasts ORDER BY id DESC")
    suspend fun getAll(): List<ForecastResponse>
}
