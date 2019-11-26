package io.github.rybalkinsd.kohttp.kohttp_dagger.second

import androidx.lifecycle.ViewModel
import okhttp3.Call
import javax.inject.Inject

class SecondViewModel @Inject constructor(
    private val callFactory: Call.Factory
) : ViewModel() {}
