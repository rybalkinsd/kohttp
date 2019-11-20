package io.github.rybalkinsd.kohttp.spring

import io.github.rybalkinsd.kohttp.client.client
import io.github.rybalkinsd.kohttp.interceptors.logging.HttpLoggingInterceptor
import okhttp3.Call
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

/**
 * Basic application context initialization
 * ComponentScan will also trigger initialization in
 * 	- [Configuration]
 * 	- [Controller]
 */
@SpringBootApplication
class SpringKohttpApplication {

	/**
	 * Specific client to handle potentially relatively slow server responses
	 */
	@Bean
	fun extendedTimeoutClient(): Call.Factory = client {
		// 10 minutes timeout
		readTimeout = 10 * 60 * 1000
		interceptors {
			+HttpLoggingInterceptor()
		}
	}
}

/**
 * Application entry point
 */
fun main(args: Array<String>) {
	runApplication<SpringKohttpApplication>(*args)
}
