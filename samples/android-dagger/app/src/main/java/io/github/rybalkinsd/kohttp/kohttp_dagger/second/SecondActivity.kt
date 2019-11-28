package io.github.rybalkinsd.kohttp.kohttp_dagger.second

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.rybalkinsd.kohttp.kohttp_dagger.App
import io.github.rybalkinsd.kohttp.kohttp_dagger.R
import io.github.rybalkinsd.kohttp.kohttp_dagger.ViewModelFactory
import io.github.rybalkinsd.kohttp.kohttp_dagger.injectViewModel
import javax.inject.Inject

class SecondActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: SecondViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appComponent = (application as App).applicationComponent
        DaggerSecondComponent.factory().create(appComponent).inject(this)
        setContentView(R.layout.activity_second)
        viewModel = injectViewModel(viewModelFactory)
    }

}
