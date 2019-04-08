package com.example.localbike.ui.fragment.stationdetails

import com.example.localbike.R
import com.example.localbike.app.AppConfig
import com.example.localbike.data.entity.StationEntity
import com.example.localbike.location.LocationProvider
import com.example.localbike.utils.ResourceManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlin.math.roundToInt

class StationDetailsPresenter(
    private val model: StationDetailsModel,
    private val locationProvider: LocationProvider,
    private val resourceManager: ResourceManager,
    private val view: StationDetailsView
) {

    private var isStopped = true
    private var map: GoogleMap? = null
    private var stationEntity: StationEntity? = null

    fun onStart() {
        isStopped = false
    }

    fun onStop() {
        isStopped = true
    }

    fun initViews(stationId: String) {
        isStopped = false
        view.showProgress()
        model.getStationById(stationId, generateStationObserver())
        initMap()
    }

    private fun generateStationObserver(): Observer<StationEntity> {
        return object : Observer<StationEntity> {
            override fun onComplete() {}

            override fun onSubscribe(d: Disposable) {}

            override fun onNext(stationEntity: StationEntity) {
                if (isStopped) return
                view.hideProgress()
                initData(stationEntity)
            }

            override fun onError(e: Throwable) {
                view.informDownloadError()
            }
        }
    }

    private fun initData(stationEntity: StationEntity) {
        this.stationEntity = stationEntity
        view.setTitle(stationEntity.placeName ?: "")
        view.setRange("")
        view.setBikeAvailability(stationEntity.bikesCount ?: "")
        view.setPlaceAvailability(stationEntity.freeRacks ?: "")
    }

    private fun initMap() {
        val map = SupportMapFragment.newInstance()
        view.initMap(map)
        map.getMapAsync {
            if (isStopped) return@getMapAsync
            this.map = it
            setMapBoundaries(it)
            getUserLocation()
            fillMapWithData()
        }
    }

    private fun setMapBoundaries(map: GoogleMap) {
        map.setLatLngBoundsForCameraTarget(
            LatLngBounds(
                LatLng(AppConfig.MAP_BOUNDRY_S, AppConfig.MAP_BOUNDRY_W),
                LatLng(AppConfig.MAP_BOUNDRY_N, AppConfig.MAP_BOUNDRY_E)

            )
        )
        map.setMinZoomPreference(AppConfig.MAP_MIN_ZOOM)
        map.setMaxZoomPreference(AppConfig.MAP_MAX_ZOOM)
    }

    private fun getUserLocation() {
        locationProvider.locationObservable.subscribe(object : Observer<LatLng> {
            override fun onComplete() {}

            override fun onSubscribe(d: Disposable) {}

            override fun onNext(location: LatLng) {
                if (isStopped) return
                addUserMarker(location)
                view.setRange(
                    getDistance(location) ?: ""
                )
            }

            override fun onError(e: Throwable) {}
        })
    }

    private fun addUserMarker(location: LatLng) {
        val markerOptions = MarkerOptions()
            .position(location)
            .icon(BitmapDescriptorFactory.defaultMarker(0f))
            .title(resourceManager.getString(R.string.user_location_prompt))
        map?.addMarker(markerOptions)
    }

    private fun fillMapWithData() {
        val latitude = stationEntity?.latitude ?: return
        val longitude = stationEntity?.longitude ?: return
        val destination = LatLng(latitude, longitude)
        val markerOptions = MarkerOptions()
            .position(destination)
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.bike_marker_bitmap))
            .title(resourceManager.getString(R.string.station_location_prompt))
        map?.addMarker(markerOptions)
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(destination, 15f)
        map?.animateCamera(cameraUpdate)
    }

    private fun getDistance(location: LatLng): String? {
        val latitude = stationEntity?.latitude ?: return null
        val longitude = stationEntity?.longitude ?: return null
        val destination = LatLng(latitude, longitude)
        return locationProvider.calculateDistance(location, destination).roundToInt().toString()
    }
}