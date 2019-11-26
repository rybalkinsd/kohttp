package io.github.rybalkinsd.kohttp.kohttp_dagger.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import io.github.rybalkinsd.kohttp.kohttp_dagger.ViewModelFactory

@Module
abstract class ViewModelModule {

    @Binds
    @ActivityScope
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}
