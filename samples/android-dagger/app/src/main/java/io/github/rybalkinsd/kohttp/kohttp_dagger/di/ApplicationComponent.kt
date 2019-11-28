package io.github.rybalkinsd.kohttp.kohttp_dagger.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import io.github.rybalkinsd.kohttp.kohttp_dagger.LocationRepo
import io.github.rybalkinsd.kohttp.kohttp_dagger.WeatherApi
import okhttp3.Call
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        LocationModule::class,
        NetworkModule::class,
        WeatherApiModule::class
    ]
)
interface ApplicationComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }

    fun locationRepo(): LocationRepo

    fun callFactory(): Call.Factory

    fun weatherApi(): WeatherApi

}
