package io.github.rybalkinsd.kohttp.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import java.net.SocketTimeoutException

/**
 * Retry Interceptor
 *
 * Retry request if you get an exception or an error code.
 *
 * @param failureThreshold number of attempts to get response (default value is 3)
 * @param invocationTimeout timeout (millisecond) before retry
 * @param ratio ratio for exponential increase of invocation timeout
 * @param _errorStatuses error codes that you need to handle
 *
 * @since 0.9.0
 * @author UDarya
 * */
class RetryInterceptor(
    private val failureThreshold: Int = 3,
    private val invocationTimeout: Long = 0,
    private val ratio: Int = 1,
    _errorStatuses: List<Int> = emptyList()
) : Interceptor {
    private var errorStatuses: List<Int> = listOf(503, 504)
    init {
        errorStatuses += _errorStatuses
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var attemptsCount = 0
        var currentDelay = invocationTimeout
        while (true) {
            try {
                if (shouldDelay(attemptsCount)) {
                    currentDelay = performDelay(currentDelay)
                }
                val response = chain.proceed(request)
                if (retryBecauseErrorCode(response, attemptsCount)) {
                    attemptsCount++
                    continue
                }
                return response
            } catch (e: Exception) {
                if (retryBecauseException(e, attemptsCount)) {
                    attemptsCount++
                    continue
                }
                throw e
            }
        }
    }

    internal fun performDelay(delay: Long): Long {
        try {
            Thread.sleep(delay)
        } catch (ignored: InterruptedException) {
        }
        return delay * ratio
    }

    private fun shouldDelay(attemptsCount: Int) = invocationTimeout > 0 && attemptsCount > 0

    private fun retryBecauseException(exception: Exception, attemptsCount: Int): Boolean {
        return exception is SocketTimeoutException && attemptsCount < failureThreshold
    }

    private fun retryBecauseErrorCode(response: Response, attemptsCount: Int): Boolean {
        if (attemptsCount >= failureThreshold) return false
        return when (response.code()) {
            in errorStatuses -> true
            else -> false
        }
    }
}