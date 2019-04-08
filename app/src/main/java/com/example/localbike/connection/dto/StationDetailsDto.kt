package com.example.localbike.connection.dto

import com.google.gson.annotations.SerializedName

class StationDetailsDto {

    @SerializedName("features")
    var stationList: List<StationDataDto>? = null


    class StationDataDto {
        @SerializedName("properties")
        var details: DetailsDto? = null
        @SerializedName("geometry")
        var location: LocationDto? = null
        var id: String? = null
    }

    class DetailsDto {
        @SerializedName("free_racks")
        var freeRacks: String? = null
        @SerializedName("bikes")
        var bikesCount: String? = null
        @SerializedName("label")
        var placeName: String? = null
        @SerializedName("bike_racks")
        var bikeRacks: String? = null
    }

    class LocationDto {
        var coordinates: Array<Double>? = null
    }
}