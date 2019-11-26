package io.github.rybalkinsd.kohttp.kohttp_dagger

import android.app.Application
import io.github.rybalkinsd.kohttp.kohttp_dagger.di.ApplicationComponent
import io.github.rybalkinsd.kohttp.kohttp_dagger.di.DaggerApplicationComponent

class App : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.factory().create(this)
    }

}
