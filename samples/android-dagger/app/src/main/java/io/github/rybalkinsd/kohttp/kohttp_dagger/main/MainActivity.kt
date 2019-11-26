package io.github.rybalkinsd.kohttp.kohttp_dagger.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.rybalkinsd.kohttp.kohttp_dagger.App
import io.github.rybalkinsd.kohttp.kohttp_dagger.R
import io.github.rybalkinsd.kohttp.kohttp_dagger.ViewModelFactory
import io.github.rybalkinsd.kohttp.kohttp_dagger.injectViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appComponent = (application as App).applicationComponent
        DaggerMainComponent.factory().create(appComponent).inject(this)
        setContentView(R.layout.activity_main)
        viewModel = injectViewModel(viewModelFactory)
    }

}
