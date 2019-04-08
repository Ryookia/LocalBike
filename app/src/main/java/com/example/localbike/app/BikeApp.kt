package com.example.localbike.app

import android.content.Context
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class BikeApp : DaggerApplication() {

    companion object {
        fun get(context: Context): BikeApp {
            return context.applicationContext as BikeApp
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val appComponent = DaggerAppComponent.builder().application(this).build();
        appComponent.inject(this)
        return appComponent
    }
}