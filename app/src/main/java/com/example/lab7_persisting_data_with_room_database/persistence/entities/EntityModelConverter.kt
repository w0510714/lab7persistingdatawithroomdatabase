package com.example.lab7_persisting_data_with_room_database.persistence.entities

import com.example.lab7_persisting_data_with_room_database.models.ForecastResponse as ForecastResponseModel
import com.example.lab7_persisting_data_with_room_database.persistence.entities.ForecastResponse as ForecastResponseEntity
import com.example.lab7_persisting_data_with_room_database.models.Location as LocationModel
import com.example.lab7_persisting_data_with_room_database.persistence.entities.Location as LocationEntity
import com.example.lab7_persisting_data_with_room_database.models.Current as CurrentModel
import com.example.lab7_persisting_data_with_room_database.persistence.entities.Current as CurrentEntity

class EntityModelConverter {
    fun toEntity(model: ForecastResponseModel): ForecastResponseEntity {
        return ForecastResponseEntity(
            location = toEntity(model.location),
            current = toEntity(model.current)
        )
    }

    fun toModel(entity: ForecastResponseEntity): ForecastResponseModel {
        return ForecastResponseModel(
            location = toModel(entity.location),
            current = toModel(entity.current)
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

    private fun toModel(entity: LocationEntity): LocationModel {
        return LocationModel(
            name = entity.name,
            region = entity.region,
            country = entity.country,
            lat = entity.lat,
            lon = entity.lon,
            localtime = entity.localtime
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

    private fun toModel(entity: CurrentEntity): CurrentModel {
        return CurrentModel(
            lastUpdated = entity.lastUpdated,
            tempC = entity.tempC,
            tempF = entity.tempF,
            windMph = entity.windMph,
            windKph = entity.windKph,
            windDir = entity.windDir
        )
    }
}
