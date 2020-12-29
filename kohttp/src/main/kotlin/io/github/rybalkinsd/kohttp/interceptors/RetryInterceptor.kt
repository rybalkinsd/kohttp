package io.github.rybalkinsd.kohttp.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import java.net.SocketTimeoutException

/**
 * Retry Interceptor
 *
 * Retry request if you get an exception or an error code.
 *
 * @param failureThreshold number of attempts to get response with default value 3
 * @param invocationTimeout timeout (millisecond) before retry
 * @param ratio ratio for exponential increase of invocation timeout
 * @param errorStatuses error codes that you need to handle with default values 503,504
 *
 * @since 0.9.0
 * @author UDarya
 * */
class RetryInterceptor(
    private val failureThreshold: Int = 3,
    private val invocationTimeout: Long = 0,
    private val ratio: Int = 1,
    private val errorStatuses: List<Int> = listOf(503, 504)
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var attemptsCount = 0
        var delay = invocationTimeout
        while (true) {
            try {
                if (shouldDelay(attemptsCount)) {
                    delay = performAndReturnDelay(delay)
                }
                val response = chain.proceed(request)
                if (!isRetry(response, attemptsCount)) return response
            } catch (e: SocketTimeoutException) {
                if (attemptsCount >= failureThreshold) throw e
            }
            attemptsCount++
        }
    }

    internal fun performAndReturnDelay(delay: Long): Long {
        try {
            Thread.sleep(delay)
        } catch (ignored: InterruptedException) {
        }
        return delay * ratio
    }

    private fun shouldDelay(attemptsCount: Int) = invocationTimeout > 0 && attemptsCount > 0

    internal fun isRetry(response: Response, attemptsCount: Int): Boolean =
            attemptsCount < failureThreshold && response.code in errorStatuses
}
