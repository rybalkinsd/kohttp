package io.github.rybalkinsd.kohttp.spring

import io.github.rybalkinsd.kohttp.client.defaultHttpClient
import io.github.rybalkinsd.kohttp.client.fork
import io.github.rybalkinsd.kohttp.interceptors.logging.HttpLoggingInterceptor
import okhttp3.Call
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

/**
 * Basic application context initialization
 * ComponentScan will also trigger initialization in
 * 	- WeatherController [io.github.rybalkinsd.kohttp.spring.controller.WeatherController]
 * 	- LocationController [io.github.rybalkinsd.kohttp.spring.controller.LocationController]
 * 	- LocationRepo [io.github.rybalkinsd.kohttp.spring.repo.LocationRepo]
 * 	- MockLogService [io.github.rybalkinsd.kohttp.spring.mock.MockLogServer]
 */
@SpringBootApplication
class WeatherApplication {
	/**
	 * Specific client to handle potentially relatively slow server responses
	 */
	@Bean
	fun extendedTimeoutClient(): Call.Factory = defaultHttpClient.fork {
		// 30 seconds timeout
		writeTimeout = 30 * 60 * 1000
		interceptors {
			+HttpLoggingInterceptor()
		}
	}
}


/**
 * Application entry point
 */
fun main(args: Array<String>) {
	runApplication<WeatherApplication>(*args)
}
