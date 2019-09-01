package io.github.rybalkinsd.kohttp.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import java.net.SocketTimeoutException
import kotlin.math.max

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
    private val errorStatuses: List<Int> = listOf(429, 503, 504)
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val maxDelayTime = chain.readTimeoutMillis()
        var attemptsCount = 0
        var delay = invocationTimeout
        while (true) {
            try {
                if (shouldDelay(attemptsCount)) performDelay(delay)
                val response = chain.proceed(request)
                delay = calculateNextRetry(response, attemptsCount, delay, maxDelayTime) ?: return response
            } catch (e: SocketTimeoutException) {
                if (attemptsCount >= failureThreshold) throw e
            }
            attemptsCount++
        }
    }

    private fun shouldDelay(attemptsCount: Int) = invocationTimeout > 0 && attemptsCount > 0

    private fun performDelay(delay: Long) {
        try {
            Thread.sleep(delay)
        } catch (ignored: InterruptedException) {
        }
    }

    internal fun calculateNextRetry(response: Response, attemptsCount: Int, delay: Long, maxDelayTime: Int): Long? {
        val retryAfter = response.header("Retry-After")?.toLongOrNull()?.let { it * 1000 }
        if (retryAfter != null && retryAfter > maxDelayTime) return null

        return if (attemptsCount < failureThreshold && response.code() in errorStatuses) {
            max(retryAfter ?: 0, delay * ratio)
        } else {
            null
        }
    }
}
