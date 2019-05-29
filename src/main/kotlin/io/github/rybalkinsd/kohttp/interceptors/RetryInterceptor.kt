package io.github.rybalkinsd.kohttp.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import java.lang.Exception
import java.net.SocketTimeoutException

// todo idempotent checking?
// todo what about another interceptors?
/**
 * Retry Interceptor
 *
 * Retry request if you got an exception or an error code.
 *
 * @param numOfAttempts number of attempts to get response (default value is 3)
 * @param delay delay before retry
 * @param step step for exponential increase of delay
 * */
class RetryInterceptor(
    private val numOfAttempts: Int = 3,
    private val delay: Long = 1000,
    private val step: Int = 1,
    private val errors: List<Int> = emptyList()
) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var attemptsCount = 0
        while (true) {
            try {
                val response = chain.proceed(request)
                if (retryBecauseErrorCode(response, attemptsCount)) {
                    attemptsCount++
                    continue
                }
                return response
            } catch (e: Exception) {
                if (retryBecauseEception(e, attemptsCount)) {
                    attemptsCount++
                    continue
                }
                throw e
            }
        }
    }

    internal fun retryBecauseEception(exception: Exception, attemptsCount: Int): Boolean {
        return exception is SocketTimeoutException && attemptsCount < numOfAttempts
    }

    internal fun retryBecauseErrorCode(response: Response, attemptsCount: Int): Boolean {
        if (attemptsCount >= numOfAttempts || response == null) return false
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