package io.github.rybalkinsd.kohttp.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import java.lang.StringBuilder
import java.time.Instant

/**
 * Request Logging Interceptor
 *
 * Logs HTTP requests.
 *
 * @param logger function to print message
 *
 * @since 0.8.0
 * @author gokul
 */


class LoggingInterceptor(private val logger: (String) -> Unit) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val startTime = Instant.now().toEpochMilli()
        val response = chain.proceed(chain.request())
        with(StringBuilder()){
            with(response.request()) {
                append("${method()} ${response.code()} - ${Instant.now().toEpochMilli() - startTime}ms ${url()}")
            }

            logger("[${Instant.now()}] $this")
        }
        return response
    }
}