package com.example.localbike.ui.fragment.stationdetails

import com.example.localbike.data.StationRepository
import com.example.localbike.location.LocationProvider
import com.example.localbike.scopes.FragmentScope
import com.example.localbike.utils.ResourceManager
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class StationDetailsModule {

    @Module
    companion object {

        @Provides
        @FragmentScope
        @JvmStatic
        internal fun provideStationDetailsPresenter(
            model: StationDetailsModel,
            locationProvider: LocationProvider,
            resourceManager: ResourceManager,
            view: StationDetailsView
        ) = StationDetailsPresenter(model, locationProvider, resourceManager, view)

        @Provides
        @FragmentScope
        @JvmStatic
        internal fun provideStationDetailsModel(stationRepository: StationRepository) =
            StationDetailsModel(stationRepository)
    }


    @Binds
    @FragmentScope
    internal abstract fun provideView(fragment: StationDetailsFragment): StationDetailsView

    @Binds
    @FragmentScope
    internal abstract fun provideResourceManager(fragment: StationDetailsFragment): ResourceManager
}