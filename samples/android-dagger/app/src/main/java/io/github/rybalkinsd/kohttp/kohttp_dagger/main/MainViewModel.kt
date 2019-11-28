package io.github.rybalkinsd.kohttp.kohttp_dagger.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import io.github.rybalkinsd.kohttp.kohttp_dagger.WeatherApi
import io.github.rybalkinsd.kohttp.kohttp_dagger.WeatherDto
import io.github.rybalkinsd.kohttp.kohttp_dagger.di.MainDto
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import okhttp3.Response
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val weatherApi: WeatherApi
) : ViewModel() {

    val responseLiveData: MutableLiveData<MainViewState> by lazy {
        MutableLiveData<MainViewState>().also { request(it) }
    }

    private val jsonAdapter: JsonAdapter<WeatherDto> by lazy {
        val moshi: Moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(
            WeatherDto::class.java,
            MainDto::class.java
        )
        moshi.adapter<WeatherDto>(type)
    }

    private fun request(liveData: MutableLiveData<MainViewState>) {
        viewModelScope.launch(IO) {
            weatherApi.getTemperature()
                .use { response ->
                    when (response.isSuccessful) {
                        true -> parseResultFromResponse(response)
                        false -> "Status ${response.message()}"
                    }.also { result ->
                        liveData.postValue(MainViewState(result))
                    }
                }
        }
    }

    private fun parseResultFromResponse(response: Response): String =
        "Temperature ${jsonAdapter.fromJson(response.body()?.string() ?: "")?.main?.temp
            ?: 0.0}"

}
