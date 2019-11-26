package io.github.rybalkinsd.kohttp.kohttp_dagger.second

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.github.rybalkinsd.kohttp.kohttp_dagger.ViewModelKey

@Module
abstract class SecondModule {

    @Binds
    @IntoMap
    @ViewModelKey(SecondViewModel::class)
    internal abstract fun bindSecondViewModel(viewModel: SecondViewModel): ViewModel

}
