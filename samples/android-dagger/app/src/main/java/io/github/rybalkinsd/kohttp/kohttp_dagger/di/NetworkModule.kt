package io.github.rybalkinsd.kohttp.kohttp_dagger.di

import dagger.Module
import dagger.Provides
import io.github.rybalkinsd.kohttp.client.client
import okhttp3.Call
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideCallFactory(): Call.Factory = client {}

}
