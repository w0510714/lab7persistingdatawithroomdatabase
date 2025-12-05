package com.example.lab8_advanced_user_interfaces.api

import com.example.lab8_advanced_user_interfaces.models.ForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

// Api Key is handled by the interceptor
interface WeatherApiService {

    @GET("forecast.json")
    suspend fun getForecast(
        @Query("q") location: String = "Halifax",
        @Query("days") days: Int = 1
    ): ForecastResponse
}
