package io.github.rybalkinsd.kohttp.interceptors

import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Response
import java.net.SocketTimeoutException
import java.time.Duration
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
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
                delay = calculateNextRetry(response.headers(), response.code(), attemptsCount, delay, maxDelayTime)
                        ?: return response
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

    internal fun calculateNextRetry(responseHeaders: Headers, responseCode: Int, attemptsCount: Int, delay: Long, maxDelayTime: Int): Long? {
        val retryAfter = parseRetryAfter(responseHeaders)
        if (retryAfter != null && retryAfter > maxDelayTime) return null

        return if (attemptsCount < failureThreshold && responseCode in errorStatuses) {
            max(retryAfter ?: 0, delay * ratio)
        } else {
            null
        }
    }

    internal fun parseRetryAfter(headers: Headers): Long? {
        val retryAfter = headers["Retry-After"] ?: return null
        return parseRetryAfterAsNumber(retryAfter) ?: parseRetryAfterAsDate(retryAfter)
    }

    private fun parseRetryAfterAsNumber(headerValue: String): Long? {
        return headerValue.toLongOrNull()?.let { it * 1000 }
    }

    private fun parseRetryAfterAsDate(headerValue: String): Long? {
        return try {
            val responseDate = ZonedDateTime.parse(headerValue, DateTimeFormatter.RFC_1123_DATE_TIME)
            val now = ZonedDateTime.now()
            val duration = Duration.between(now, responseDate)
            duration.toMillis().takeIf { it > 0 }
        } catch (ex: DateTimeParseException) {
            null
        }
    }
}
