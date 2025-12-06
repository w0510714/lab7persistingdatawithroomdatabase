package com.example.lab8_advanced_user_interfaces.persistence.entities

import com.example.lab8_advanced_user_interfaces.models.ForecastResponse as ForecastResponseModel
import com.example.lab8_advanced_user_interfaces.persistence.entities.ForecastResponse as ForecastResponseEntity
import com.example.lab8_advanced_user_interfaces.models.Location as LocationModel
import com.example.lab8_advanced_user_interfaces.persistence.entities.Location as LocationEntity
import com.example.lab8_advanced_user_interfaces.models.Current as CurrentModel
import com.example.lab8_advanced_user_interfaces.persistence.entities.Current as CurrentEntity

class EntityModelConverter {
    fun toEntity(model: ForecastResponseModel): ForecastResponseEntity {
        return ForecastResponseEntity(
            location = toEntity(model.location),
            current = toEntity(model.current)
        )
    }

    private fun toEntity(model: LocationModel): LocationEntity {
        return LocationEntity(
            name = model.name,
            region = model.region,
            country = model.country,
            lat = model.lat,
            lon = model.lon,
            localtime = model.localtime
        )
    }

    private fun toEntity(model: CurrentModel): CurrentEntity {
        return CurrentEntity(
            lastUpdated = model.lastUpdated,
            tempC = model.tempC,
            tempF = model.tempF,
            windMph = model.windMph,
            windKph = model.windKph,
            windDir = model.windDir
        )
    }
}
