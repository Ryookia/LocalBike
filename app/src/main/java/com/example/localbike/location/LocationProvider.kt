package com.example.localbike.location

import android.content.Context
import android.location.*
import android.os.Bundle
import com.google.android.gms.maps.model.LatLng
import io.reactivex.subjects.PublishSubject
import java.io.IOException
import java.util.*

const val LOCATION_INTERVAL = 0L
const val LOCATION_DISTANCE = 0.0f

class LocationProvider(private val context: Context) {

    private var location: LatLng? = null
    private val listener = generateLocationListener()
    var locationObservable = PublishSubject.create<LatLng>()

    fun observeLocation() {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        try {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                LOCATION_INTERVAL,
                LOCATION_DISTANCE,
                listener
            )
            locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                LOCATION_INTERVAL,
                LOCATION_DISTANCE,
                listener
            )
        } catch (e: SecurityException) {
            println(e)
        }
    }

    fun stopLocation() {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.removeUpdates(listener)
    }

    fun calculateDistance(firstPoint: LatLng, secondPoint: LatLng): Float {
        val firstLoc = Location("").also {
            it.latitude = firstPoint.latitude
            it.longitude = firstPoint.longitude
        }
        val secondLoc = Location("").also {
            it.latitude = secondPoint.latitude
            it.longitude = secondPoint.longitude
        }
        return firstLoc.distanceTo(secondLoc)
    }

    fun addressFromLocation(location: LatLng): String? {
        if (!Geocoder.isPresent()) {
            return null
        }
        val geocoder = Geocoder(context, Locale.getDefault())
        try {
            val addresses: List<Address> = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            if (addresses.isEmpty()) {
                return null
            }
            return formatAddressToStreet(addresses[0])
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    private fun formatAddressToStreet(address: Address): String? {
        if (address.maxAddressLineIndex < 0) return null
        return address.getAddressLine(0).replace(", " + address.countryName, "")
    }

    private fun generateLocationListener(): LocationListener {
        return object : LocationListener {
            override fun onLocationChanged(location: Location?) {
                if (location == null) return
                val latLng = LatLng(location.latitude, location.longitude)
                this@LocationProvider.location = latLng
                this@LocationProvider.locationObservable.onNext(latLng)
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            }

            override fun onProviderEnabled(provider: String?) {
            }

            override fun onProviderDisabled(provider: String?) {
            }
        }
    }
}