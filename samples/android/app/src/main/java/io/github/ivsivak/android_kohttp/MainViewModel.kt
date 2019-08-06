package io.github.ivsivak.android_kohttp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.rybalkinsd.kohttp.dsl.httpGet
import io.github.rybalkinsd.kohttp.ext.url
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {

    val responseLiveData: MutableLiveData<ViewResponse> by lazy {
        MutableLiveData<ViewResponse>().also { request(it) }
    }

    private fun request(liveData: MutableLiveData<ViewResponse>) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val response = httpGet {
                    url("https://google.com/search")
                    param {
                        "q" to "iphone"
                        "safe" to "off"
                    }
                }
                if (response.isSuccessful) {
                    response.body()
                        ?.string()
                        ?.also { liveData.postValue(ViewResponse(data = it)) }
                } else {
                    liveData.postValue(ViewResponse(data = "Error"))
                }
            }
        }
    }

}