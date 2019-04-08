package com.example.localbike.ui.activity.main

import com.example.localbike.R
import com.example.localbike.scopes.ActivityScope
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {
    @Provides
    @ActivityScope
    fun providesFragmentHelper(activity: MainActivity): FragmentHelper {
        return FragmentHelper(activity.supportFragmentManager, R.id.mainFrame)
    }
}