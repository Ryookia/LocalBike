package com.example.localbike.connection

import com.example.localbike.connection.dto.StationDetailsDto
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RestService {

    companion object {
        const val BASE_URL = "http://www.poznan.pl/"
        const val HOST = BASE_URL + "mim/plan/"
        const val MAP_SERVICE = "map_service.html"

        const val MAP_QUERY_TYPE = "mtype"
        const val MAP_QUERY_TARGET = "co"

        const val MAP_VALUE_PUBLIC_TRANSPORT = "pub_transport"
        const val MAP_VALUE_TARGET = "stacje_rowerowe"

    }

    @GET(MAP_SERVICE)
    fun getBikeData(
        @Query(MAP_QUERY_TARGET) targetType: String = MAP_VALUE_TARGET,
        @Query(MAP_QUERY_TYPE) transportType: String = MAP_VALUE_PUBLIC_TRANSPORT
    ): Observable<StationDetailsDto>
}