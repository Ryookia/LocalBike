package com.example.localbike.data

import com.example.localbike.connection.RestService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {
    @Provides
    @Singleton
    fun provideStationRepository(restService: RestService): StationRepository {
        return StationRepository(restService)
    }
}