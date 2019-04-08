package com.example.localbike.ui.fragment.stationdetails

import com.example.localbike.data.StationRepository
import com.example.localbike.data.entity.StationEntity
import io.reactivex.Observer

class StationDetailsModel(private val stationRepository: StationRepository) {
    fun getStationById(stationId: String, observer: Observer<StationEntity>) {
        stationRepository.getStationById(stationId, observer)
    }
}