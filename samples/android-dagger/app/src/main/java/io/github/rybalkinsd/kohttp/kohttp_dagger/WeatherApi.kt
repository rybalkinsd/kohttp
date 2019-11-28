package io.github.rybalkinsd.kohttp.kohttp_dagger

import io.github.rybalkinsd.kohttp.dsl.httpGet
import okhttp3.Call
import okhttp3.Response
import javax.inject.Inject

interface WeatherApi {

    suspend fun getTemperature(): Response

}

class WeatherApiImpl @Inject constructor(
    private val callFactory: Call.Factory,
    private val locationRepo: LocationRepo
) : WeatherApi {

    override suspend fun getTemperature(): Response = httpGet(callFactory) {
        scheme = "https"
        host = "openweathermap.org"
        path = "/data/2.5/weather"

        param {
            "q" to locationRepo.location
            "appid" to "b6907d289e10d714a6e88b30761fae22"
            "units" to "metric"
        }
    }

}
