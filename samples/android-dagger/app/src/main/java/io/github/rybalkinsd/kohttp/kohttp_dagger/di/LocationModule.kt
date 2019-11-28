package io.github.rybalkinsd.kohttp.kohttp_dagger.di

import dagger.Binds
import dagger.Module
import io.github.rybalkinsd.kohttp.kohttp_dagger.LocationRepo
import io.github.rybalkinsd.kohttp.kohttp_dagger.LocationRepoImpl
import javax.inject.Singleton

@Module
abstract class LocationModule {

    @Binds
    @Singleton
    abstract fun bindLocationRepo(locationRepo: LocationRepoImpl): LocationRepo

}
