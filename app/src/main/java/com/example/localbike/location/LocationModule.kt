package com.example.localbike.location

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocationModule {
    @Provides
    @Singleton
    fun provideLocationProvider(context: Context): LocationProvider {
        return LocationProvider(context)
    }
}