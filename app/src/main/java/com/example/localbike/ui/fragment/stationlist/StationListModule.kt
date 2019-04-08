package com.example.localbike.ui.fragment.stationlist

import com.example.localbike.data.StationRepository
import com.example.localbike.location.LocationProvider
import com.example.localbike.scopes.FragmentScope
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class StationListModule {

    @Module
    companion object {

        @Provides
        @FragmentScope
        @JvmStatic
        internal fun provideStationListPresenter(
            model: StationListModel, locationProvider: LocationProvider, view: StationListView
        ) = StationListPresenter(model, locationProvider, view)

        @Provides
        @FragmentScope
        @JvmStatic
        internal fun provideStationListModel(stationRepository: StationRepository) = StationListModel(stationRepository)
    }


    @Binds
    @FragmentScope
    internal abstract fun provideView(fragment: StationListFragment): StationListView
}