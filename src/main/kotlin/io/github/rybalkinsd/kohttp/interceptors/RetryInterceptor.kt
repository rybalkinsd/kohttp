package io.github.rybalkinsd.kohttp.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import java.lang.Exception
import java.net.SocketTimeoutException

/**
 * Retry Interceptor
 *
 * Retry request if you get an exception or an error code.
 *
 * @param failureThreshold number of attempts to get response (default value is 3)
 * @param invocationTimeout timeout (millisecond) before retry
 * @param step step for exponential increase of invocation timeout
 * @param errors error codes that you need to handle
 *
 * @since 0.9.0
 * @author UDarya
 * */
class RetryInterceptor(
    private val failureThreshold: Int = 3,
    private val invocationTimeout: Long = 0,
    private val step: Int = 1,
    private val errors: List<Int> = emptyList()
) : Interceptor {
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
        } catch (ignored: InterruptedException) {}
        return delay * step
    }

    private fun shouldDelay(attemptsCount: Int) = invocationTimeout > 0 && attemptsCount > 0

    private fun retryBecauseException(exception: Exception, attemptsCount: Int): Boolean {
        return exception is SocketTimeoutException && attemptsCount < failureThreshold
    }

    private fun retryBecauseErrorCode(response: Response, attemptsCount: Int): Boolean {
        if (attemptsCount >= failureThreshold || response == null) return false
        return when (response.code()) {
            in defaultResponseCodeList -> true
            in errors -> true
            else -> false
        }
    }

    private companion object {
        val defaultResponseCodeList = listOf(503, 504)
    }

}