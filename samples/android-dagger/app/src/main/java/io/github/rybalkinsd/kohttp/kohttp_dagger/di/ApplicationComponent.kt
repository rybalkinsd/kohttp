package io.github.rybalkinsd.kohttp.kohttp_dagger.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import okhttp3.Call
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface ApplicationComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }

    fun callFactory(): Call.Factory

}
