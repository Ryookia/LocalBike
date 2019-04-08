package com.example.localbike.utils

import com.example.localbike.connection.dto.StationDetailsDto
import com.example.localbike.data.entity.StationEntity

class DtoConverter {
    companion object {
        fun stationDetailsDtoToStationEntity(stationDetailsDto: StationDetailsDto): List<StationEntity> {
            val result = ArrayList<StationEntity>()
            stationDetailsDto.stationList?.forEach {
                var latitude: Double? = null
                var longitude: Double? = null
                it.location?.coordinates?.let {
                    if (it.size < 2) return@let
                    try {
                        latitude = it[1]
                        longitude = it[0]
                    } catch (e: Exception) {
                    }
                }
                result.add(
                    StationEntity(
                        it.id,
                        it.details?.freeRacks,
                        it.details?.bikesCount,
                        it.details?.placeName,
                        it.details?.bikeRacks,
                        latitude,
                        longitude
                    )
                )
            }
            return result
        }
    }
}