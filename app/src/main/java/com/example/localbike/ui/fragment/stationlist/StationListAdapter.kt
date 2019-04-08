package com.example.localbike.ui.fragment.stationlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.localbike.R
import com.example.localbike.data.entity.StationEntity
import com.example.localbike.location.LocationProvider
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Observer
import kotlin.math.roundToInt

class StationListAdapter(
    private val stationList: List<StationEntity>,
    private val locationProvider: LocationProvider,
    private val observer: Observer<Int>
) : RecyclerView.Adapter<StationListHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationListHolder {
        return StationListHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_station, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return stationList.size
    }

    override fun onBindViewHolder(holder: StationListHolder, position: Int) {
        val item = getItem(position)
        holder.layout.setOnClickListener { observer.onNext(position) }
        holder.title.text = item?.placeName ?: ""
        val location = getLocationFromItem(item)
        holder.range.text = ""
        if (location != null) {
            holder.address.text = locationProvider.addressFromLocation(location)
            holder.setSubscription(locationProvider.locationObservable.subscribe {
                holder.range.text = locationProvider.calculateDistance(it, location).roundToInt().toString() + " m"
            })
        }
        holder.bikeAvailability.text = item?.bikesCount ?: "0"
        holder.placeAvailability.text = item?.bikeRacks ?: "0"

    }

    private fun getLocationFromItem(stationEntity: StationEntity?): LatLng? {
        val latitude = stationEntity?.latitude ?: return null
        val longitude = stationEntity?.longitude ?: return null
        return LatLng(latitude, longitude)
    }

    private fun getItem(position: Int): StationEntity? {
        if (position < 0 || position > stationList.size) return null
        return stationList[position]
    }
}