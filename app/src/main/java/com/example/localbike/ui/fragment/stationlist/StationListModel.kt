package com.example.localbike.ui.fragment.stationlist

import com.example.localbike.data.StationRepository
import com.example.localbike.data.entity.StationEntity
import io.reactivex.Observer

class StationListModel(private val stationRepository: StationRepository) {
    fun getStationList(observer: Observer<List<StationEntity>>) {
        stationRepository.getStationList(observer)
    }
}