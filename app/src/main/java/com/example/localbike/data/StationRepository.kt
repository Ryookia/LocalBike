package com.example.localbike.data

import com.example.localbike.connection.RestService
import com.example.localbike.connection.dto.StationDetailsDto
import com.example.localbike.data.entity.StationEntity
import com.example.localbike.utils.DtoConverter
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class StationRepository(private val restService: RestService) {

    private var stationList: List<StationEntity>? = null

    fun getLiveStationList(observer: Observer<List<StationEntity>>) {
        restService.getBikeData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(generateRestObserver(observer))
    }

    fun getStationList(observer: Observer<List<StationEntity>>) {
        stationList?.let {
            observer.onNext(it)
            return
        }
        getLiveStationList(observer)
    }


    fun getStationById(id: String, observer: Observer<StationEntity>) {
        stationList?.forEach {
            if (it.id == id) {
                observer.onNext(it)
                return
            }
        }
        observer.onError(Throwable("Station by given id does not exists"))
    }

    private fun generateRestObserver(observer: Observer<List<StationEntity>>): Observer<StationDetailsDto> {
        return object : Observer<StationDetailsDto> {
            override fun onComplete() {}

            override fun onSubscribe(d: Disposable) {

            }

            override fun onNext(stationDto: StationDetailsDto) {
                val convertedData = DtoConverter.stationDetailsDtoToStationEntity(stationDto)
                stationList = convertedData
                observer.onNext(convertedData)
            }

            override fun onError(e: Throwable) {
                observer.onError(e)
            }
        }
    }
}