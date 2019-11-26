package io.github.rybalkinsd.kohttp.kohttp_dagger.main

import androidx.lifecycle.ViewModel
import okhttp3.Call
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val callFactory: Call.Factory
) : ViewModel() {
}
