package com.example.localbike.ui.activity

import com.example.localbike.scopes.ActivityScope
import com.example.localbike.ui.activity.main.MainActivity
import com.example.localbike.ui.activity.main.MainActivityModule
import com.example.localbike.ui.fragment.FragmentProvider
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [MainActivityModule::class, FragmentProvider::class])
    internal abstract fun bindMainActivity(): MainActivity
}