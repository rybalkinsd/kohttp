package io.github.ivsivak.android_kohttp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import io.github.rybalkinsd.kohttp.dsl.httpGet
import io.github.rybalkinsd.kohttp.ext.url
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {

    val responseLiveData: MutableLiveData<ViewResponse> by lazy {
        MutableLiveData<ViewResponse>().also { request(it) }
    }

    private val jsonAdapter: JsonAdapter<List<Repository>> by lazy {
        val moshi: Moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, Repository::class.java)
        moshi.adapter<List<Repository>>(type)
    }

    private fun request(liveData: MutableLiveData<ViewResponse>) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val response = httpGet {
                    url("https://api.github.com/users/rybalkinsd/repos")
                }
                if (response.isSuccessful) {
                    val repos = jsonAdapter.fromJson(response.body()?.string() ?: "")
                    liveData.postValue(
                        ViewResponse(
                            status = "Response status ${response.message()}",
                            list = repos ?: emptyList()
                        )
                    )
                } else {
                    liveData.postValue(
                        ViewResponse(
                            status = "Response status ${response.message()}",
                            list = emptyList()
                        )
                    )
                }
            }
        }
    }

}