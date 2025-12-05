package com.example.lab8_advanced_user_interfaces.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lab8_advanced_user_interfaces.persistence.dao.ForecastDao
import com.example.lab8_advanced_user_interfaces.persistence.entities.ForecastResponse

@Database(entities = [ForecastResponse::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun forecastDao(): ForecastDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "forecast_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
