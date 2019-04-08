package com.example.localbike.ui.fragment

import com.example.localbike.scopes.FragmentScope
import com.example.localbike.ui.fragment.stationdetails.StationDetailsFragment
import com.example.localbike.ui.fragment.stationdetails.StationDetailsModule
import com.example.localbike.ui.fragment.stationlist.StationListFragment
import com.example.localbike.ui.fragment.stationlist.StationListModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentProvider {
    @FragmentScope
    @ContributesAndroidInjector(modules = [StationDetailsModule::class])
    internal abstract fun provideStationDetailsFragment(): StationDetailsFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [StationListModule::class])
    internal abstract fun provideStationListFragment(): StationListFragment
}