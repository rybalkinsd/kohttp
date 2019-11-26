package io.github.rybalkinsd.kohttp.kohttp_dagger.main

import dagger.Component
import io.github.rybalkinsd.kohttp.kohttp_dagger.di.ActivityScope
import io.github.rybalkinsd.kohttp.kohttp_dagger.di.ApplicationComponent

@ActivityScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [MainModule::class]
)
interface MainComponent {

    @Component.Factory
    interface Factory {
        fun create(applicationComponent: ApplicationComponent): MainComponent
    }

    fun inject(activity: MainActivity)

}
