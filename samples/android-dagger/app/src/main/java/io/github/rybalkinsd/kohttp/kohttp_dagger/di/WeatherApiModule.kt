package io.github.rybalkinsd.kohttp.kohttp_dagger.di

import dagger.Binds
import dagger.Module
import io.github.rybalkinsd.kohttp.kohttp_dagger.WeatherApi
import io.github.rybalkinsd.kohttp.kohttp_dagger.WeatherApiImpl
import javax.inject.Singleton

@Module
abstract class WeatherApiModule {

    @Binds
    @Singleton
    abstract fun bindWeatherApi(weatherApi: WeatherApiImpl): WeatherApi

}
