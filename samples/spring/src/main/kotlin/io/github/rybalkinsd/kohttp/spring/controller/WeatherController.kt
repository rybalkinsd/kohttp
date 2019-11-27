package io.github.rybalkinsd.kohttp.spring.controller

import io.github.rybalkinsd.kohttp.dsl.async.httpPostAsync
import io.github.rybalkinsd.kohttp.dsl.httpGet
import io.github.rybalkinsd.kohttp.ext.url
import io.github.rybalkinsd.kohttp.jackson.ext.toJson
import io.github.rybalkinsd.kohttp.spring.repo.LocationRepo
import okhttp3.Call
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * [WeatherController] accept
 * GET /weather request - to access temperature in location,
 * provided by [LocationRepo]
 *
 * @author Sergey
 */
@RestController
class WeatherController(
    private val extendedTimeoutClient: Call.Factory,
    private val locationRepo: LocationRepo
) {
    /**
     * This method uses [kohttp] in two cases:
     *  1. [httpGet] - To get real weather data from openweathermap.org
     *  2. [httpPostAsync] - To asynchronously log data to our Log Service
     *
     * This method also uses [kohttp-jackson] to manipulate weather data
     * as JSON
     *
     * @return temperature in Celsius in location provided by [LocationRepo]
     */
    @GetMapping("/weather")
    fun getHandle(): Double {
        /**
         * Get weather data from OpenWeatherMap.org 
         */
        val temperature = httpGet {
            scheme = "https"
            host = "openweathermap.org"
            path = "/data/2.5/weather"

            param {
                "q" to locationRepo.location
                "appid" to "b6907d289e10d714a6e88b30761fae22"
                "units" to "metric"
            }
        }.use {
            it.toJson()["main"]["temp"].doubleValue()
        }

        /**
         * Asynchronously log received data
         * Status of this request will be tracked with the help of [HttpLoggingInterceptor]
         * which is integrated in [extendedTimeoutClient]
         */
        httpPostAsync(extendedTimeoutClient) {
            // we're using a locally-deployed Log Server
            url("http://localhost:8080/log/weather")
            body {
                json {
                    "timestamp" to System.currentTimeMillis()
                    "location" to locationRepo.location
                    "temperature" to temperature
                }
            }
        }

        return temperature
    }
}
