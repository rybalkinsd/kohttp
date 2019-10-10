package io.github.rybalkinsd.kohttp.interceptors

import io.github.rybalkinsd.kohttp.client.defaultHttpClient
import io.github.rybalkinsd.kohttp.client.fork
import io.github.rybalkinsd.kohttp.dsl.httpGet
import io.github.rybalkinsd.kohttp.interceptors.logging.HttpLoggingInterceptor
import io.mockk.spyk
import io.mockk.verify
import okhttp3.*
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.mockserver.client.MockServerClient
import org.mockserver.junit.MockServerRule
import org.mockserver.matchers.Times.exactly
import org.mockserver.model.Delay
import org.mockserver.model.HttpRequest.request
import org.mockserver.model.HttpResponse.response
import org.mockserver.verify.VerificationTimes
import java.net.SocketTimeoutException
import java.security.MessageDigest
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull


class RetryInterceptorTest {
    private val mockServerPort: Int = 1080
    private val localhost = "127.0.0.1"
    private val mockPath = "/mock"

    @Rule
    @JvmField
    var mockServerRule = MockServerRule(this, mockServerPort)

    @Test
    fun `retry default times if timeout exception`() {
        val numOfRetry = 3
        val retryInterceptor = spyk(RetryInterceptor())
        createExpectationForGetWithResponseCode(numOfRetry + 1, 200, 10)
        try {
            getCall(getHttpClientWithConnectTimeoutAndInterceptors(retryInterceptor, 1))
        } catch (e: SocketTimeoutException) {

        }

        verifyGetRequest(numOfRetry + 1)
    }

    @Test
    fun `retry 5 times if socket timeout exception`() {
        val numOfRetry = 5
        createExpectationForGetWithResponseCode(numOfRetry + 1, 200, 10)
        val retryInterceptor = spyk(RetryInterceptor(failureThreshold = numOfRetry, invocationTimeout = 100))
        try {
            getCall(getHttpClientWithConnectTimeoutAndInterceptors(retryInterceptor, 1))
        } catch (ignored: SocketTimeoutException) {

        }

        verifyGetRequest(numOfRetry + 1)
    }

    @Test
    fun `retry if gateway timeout`() {
        val numOfRetry = 2
        createExpectationForGetWithResponseCode(numOfRetry, 504)
        val retryInterceptor = spyk(RetryInterceptor(failureThreshold = numOfRetry))
        getCall(getHttpClientWithConnectTimeoutAndInterceptors(retryInterceptor))

        verifyGetRequest(numOfRetry + 1)
    }

    @Test
    fun `retry if service unavailable`() {
        val numOfRetry = 2
        createExpectationForGetWithResponseCode(numOfRetry, 503)
        val retryInterceptor = spyk(RetryInterceptor(failureThreshold = numOfRetry))
        getCall(getHttpClientWithConnectTimeoutAndInterceptors(retryInterceptor))

        verifyGetRequest(numOfRetry + 1)
    }

    @Test
    fun `don't retry if success response`() {
        val numOfRetry = 2
        createExpectationForGetWithResponseCode(numOfRetry, 200)
        val retryInterceptor = spyk(RetryInterceptor())
        val client = getHttpClientWithConnectTimeoutAndInterceptors(retryInterceptor)
        getCall(client)

        verifyGetRequest(1)
    }

    @Test
    fun `delay increase by ratio`() {
        val ratio = 2
        val retryInterceptor = RetryInterceptor(ratio = ratio)
        val invocationTimeout: Long = 1000
        val responseHeaders = Headers.of()
        val responseCode = 429
        assertEquals(invocationTimeout * ratio, retryInterceptor.calculateNextRetry(responseHeaders, responseCode, 0, invocationTimeout, 10000))
    }

    @Test
    fun `retry just next interceptors`() {
        val urlEncoder = Base64.getUrlEncoder()
        val md5 = MessageDigest.getInstance("md5")
        val signingInterceptorSpy = spyk(SigningInterceptor("key") {
            val query = (query() ?: "").toByteArray()
            urlEncoder.encodeToString(md5.digest(query))
        })
        val loggingInterceptorSpy = spyk(HttpLoggingInterceptor())

        createExpectationForGetWithResponseCode(4, 503)
        val client = defaultHttpClient.fork {
            interceptors {
                +signingInterceptorSpy
                +RetryInterceptor()
                +loggingInterceptorSpy
            }
        }
        getCall(client)
        verify(exactly = 1) { signingInterceptorSpy.intercept(any()) }
        verify(exactly = 4) { loggingInterceptorSpy.intercept(any()) }
    }

    @Test
    fun `custom errors processing`() {
        val numOfRetry = 2
        createExpectationForGetWithResponseCode(numOfRetry, 408)
        val retryInterceptor = spyk(RetryInterceptor(errorStatuses = listOf(408)))
        val client = getHttpClientWithConnectTimeoutAndInterceptors(retryInterceptor)
        getCall(client)

        verifyGetRequest(numOfRetry + 1)
    }

    @Test
    fun `not need retry if status code is 200`() {
        val request = Request.Builder().url(HttpUrl.Builder().host(localhost).scheme("http").build()).build()
        val response = Response.Builder().code(200).protocol(Protocol.HTTP_1_1).message("").request(request).build()
        Assert.assertNull(RetryInterceptor().calculateNextRetry(response.headers(), response.code(), 1, 2, 30))
    }

    @Test
    fun `need retry if status code in error codes list`() {
        val request = Request.Builder().url(HttpUrl.Builder().host(localhost).scheme("http").build()).build()
        val response = Response.Builder().code(503).protocol(Protocol.HTTP_1_1).message("").request(request).build()
        Assert.assertNotNull(RetryInterceptor().calculateNextRetry(response.headers(), response.code(), 1, 2, 30))
    }

    @Test
    fun `get Retry-After Header's value as next retry`() {
        val maxDelayTime = 2001
        val retryAfterSeconds = 2L
        val retryAfter = RetryInterceptor().calculateNextRetry(Headers.of(mapOf("Retry-After" to retryAfterSeconds.toString())), 429, 1, 2, maxDelayTime)
        assertEquals(retryAfterSeconds * 1000, retryAfter)
    }

    @Test
    fun `get null as next retry if Retry-After Header's value exceeds maxDelayTime`() {
        val maxDelayTime = 1999
        val retryAfterSeconds = 2L
        val retryAfter = RetryInterceptor().calculateNextRetry(Headers.of(mapOf("Retry-After" to retryAfterSeconds.toString())), 429, 1, 2, maxDelayTime)
        assertNull(retryAfter)
    }

    @Test
    fun `parse RetryAfter header whose unit is second`() {
        val interceptor = RetryInterceptor()
        val retryAfter = interceptor.parseRetryAfter(Headers.of(mapOf("Retry-After" to "2")))
        assertEquals(2 * 1000, retryAfter)
    }

    @Test
    fun `parse RetryAfter header whose unit is date`() {
        val interceptor = RetryInterceptor()
        val retryAfter = interceptor.parseRetryAfter(Headers.of(mapOf("Retry-After" to "Mon, 21 Oct 2115 07:28:00 GMT")))
        assertNotNull(retryAfter)
    }

    @Test
    fun `return null if RetryAfter header's date value is invalid`() {
        val interceptor = RetryInterceptor()
        val retryAfter = interceptor.parseRetryAfter(Headers.of(mapOf("Retry-After" to "Wed,, 21 Oct 2115 07:28:00 GMT")))
        assertNull(retryAfter)
    }

    private fun getCall(client: OkHttpClient) {
        httpGet(client = client) {
            host = localhost
            port = mockServerPort
            path = mockPath
        }
    }

    private fun getHttpClientWithConnectTimeoutAndInterceptors(
        retryInterceptor: RetryInterceptor,
        connectionTimeout: Long = 10
    ) = defaultHttpClient.fork {
        connectTimeout = TimeUnit.SECONDS.toMillis(connectionTimeout)
        interceptors { +retryInterceptor }
    }

    private fun createExpectationForGetWithResponseCode(count: Int, statusCode: Int, delay: Long = 0) {
        MockServerClient(localhost, mockServerPort)
            .`when`(
                request()
                    .withMethod("GET")
                    .withPath(mockPath),
                exactly(count)
            )
            .respond(
                response()
                    .withStatusCode(statusCode)
                    .withDelay(Delay(TimeUnit.SECONDS, delay))
            )
    }

    private fun verifyGetRequest(requestNum: Int) {
        MockServerClient(localhost, mockServerPort).verify(
            request()
                .withMethod("GET")
                .withPath(mockPath),
            VerificationTimes.exactly(requestNum)
        )
    }

}