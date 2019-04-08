package com.example.localbike.ui.fragment.stationdetails

import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.SupportMapFragment

interface StationDetailsView {
    fun showProgress()
    fun hideProgress()
    fun setTitle(title: String)
    fun setRange(range: String)
    fun setBikeAvailability(bikeAvailability: String)
    fun setPlaceAvailability(placeAvailability: String)
    fun getMapView(): MapView
    fun initMap(fragment: SupportMapFragment)
    fun informDownloadError()
}