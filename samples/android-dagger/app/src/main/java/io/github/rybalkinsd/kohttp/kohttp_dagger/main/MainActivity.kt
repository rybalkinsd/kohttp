package io.github.rybalkinsd.kohttp.kohttp_dagger.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import io.github.rybalkinsd.kohttp.kohttp_dagger.App
import io.github.rybalkinsd.kohttp.kohttp_dagger.R
import io.github.rybalkinsd.kohttp.kohttp_dagger.ViewModelFactory
import io.github.rybalkinsd.kohttp.kohttp_dagger.injectViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appComponent = (application as App).applicationComponent
        DaggerMainComponent.factory().create(appComponent).inject(this)
        setContentView(R.layout.activity_main)
        viewModel = injectViewModel(viewModelFactory)
    }

    override fun onStart() {
        super.onStart()
        viewModel.responseLiveData.observe(this, Observer {
            main_text_view.text = it.state
        })
    }

}
