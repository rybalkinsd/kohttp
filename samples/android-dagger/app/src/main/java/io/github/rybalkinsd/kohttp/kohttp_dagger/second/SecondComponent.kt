package io.github.rybalkinsd.kohttp.kohttp_dagger.second

import dagger.Component
import io.github.rybalkinsd.kohttp.kohttp_dagger.di.ActivityScope
import io.github.rybalkinsd.kohttp.kohttp_dagger.di.ApplicationComponent

@ActivityScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [SecondModule::class]
)
interface SecondComponent {

    @Component.Factory
    interface Factory {
        fun create(applicationComponent: ApplicationComponent): SecondComponent
    }

    fun inject(activity: SecondActivity)

}
