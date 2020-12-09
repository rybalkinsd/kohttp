package io.github.rybalkinsd.kohttp.android_kohttp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import io.github.rybalkinsd.kohttp.dsl.httpGet
import io.github.rybalkinsd.kohttp.ext.url
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {

    val responseLiveData: MutableLiveData<ViewResponse> by lazy {
        MutableLiveData<ViewResponse>().also { request(it) }
    }

    private val jsonAdapter: JsonAdapter<Discover> by lazy {
        val moshi: Moshi = Moshi.Builder().build()
        moshi.adapter(Discover::class.java)
    }

    private fun request(liveData: MutableLiveData<ViewResponse>) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                httpGet {
                    url("http://wtfis.ru:8080/flit/discover")
                }.use { response ->
                    if (response.isSuccessful) {
                        val discover = jsonAdapter.fromJson(response.body()?.string() ?: "")
                        liveData.postValue(
                            ViewResponse(
                                status = "Response status ${response.message()}",
                                discover = discover ?: Discover()
                            )
                        )
                    } else {
                        liveData.postValue(
                            ViewResponse(
                                status = "Response status ${response.message()}",
                                discover = Discover()
                            )
                        )
                    }
                }
            }
        }
    }

}
