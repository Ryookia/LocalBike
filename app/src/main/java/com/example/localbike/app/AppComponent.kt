package com.example.localbike.app

import android.app.Application
import com.example.localbike.connection.RestServiceModule
import com.example.localbike.data.DataModule
import com.example.localbike.location.LocationModule
import com.example.localbike.ui.activity.ActivityModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityModule::class,
        AppModule::class,
        DataModule::class,
        LocationModule::class,
        RestServiceModule::class
    ]
)
interface AppComponent : AndroidInjector<DaggerApplication> {

    fun inject(app: BikeApp)

    override fun inject(instance: DaggerApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}