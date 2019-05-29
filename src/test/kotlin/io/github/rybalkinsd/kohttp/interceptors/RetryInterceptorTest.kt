package io.github.rybalkinsd.kohttp.interceptors

import io.github.rybalkinsd.kohttp.client.defaultHttpClient
import io.github.rybalkinsd.kohttp.client.fork
import io.github.rybalkinsd.kohttp.dsl.httpGet
import io.mockk.spyk
import io.mockk.verify
import okhttp3.OkHttpClient
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockserver.client.MockServerClient
import org.mockserver.integration.ClientAndServer
import org.mockserver.integration.ClientAndServer.startClientAndServer
import org.mockserver.matchers.Times.exactly
import org.mockserver.model.HttpRequest.request
import org.mockserver.model.HttpResponse.response
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit


class RetryInterceptorTest {

    private lateinit var mockServer: ClientAndServer
    private val mockServerPort: Int = 1080
    private val localhost = "127.0.0.1"

    @Before
    fun startServer() {
        mockServer = startClientAndServer(mockServerPort)
    }

    @After
    fun stopServer() {
        mockServer.stop()
    }

    @Test
    fun `retry default times if timeout exception`() {
        val retryInterceptor = spyk(RetryInterceptor())
        val client = getHttpClientWithConnectTimeoutAndInterceptors(retryInterceptor, 3)
        try {
            callGetWithResponseAfterDelay(client)
        } catch (e: SocketTimeoutException) {

        }

        verify {
            retryInterceptor.retryBecauseEception(any(), any())
        }
    }

    @Test
    fun `retry 5 times if timeout exception`() {
        val numOfRetry = 5
        val retryInterceptor = spyk(RetryInterceptor(numOfAttempts = numOfRetry))
        val client = getHttpClientWithConnectTimeoutAndInterceptors(retryInterceptor, 3)
        try {
            callGetWithResponseAfterDelay(client)
        } catch (e: SocketTimeoutException) {

        }

        verify(exactly = numOfRetry + 1) {
            retryInterceptor.retryBecauseEception(any(), any())
        }
    }

    @Test
    fun `retry if gateway timeout `() {
        val numOfRetry = 2
        createExpectationForGetWithGatewayTimeout(numOfRetry)
        val retryInterceptor = spyk(RetryInterceptor(numOfAttempts = numOfRetry))
        val client = getHttpClientWithConnectTimeoutAndInterceptors(retryInterceptor, 10)
        httpGet(client = client) {
            host = localhost
            port = mockServerPort
            path = "/gatewayTimeout"
        }

        verify(exactly = numOfRetry + 1) {
            retryInterceptor.retryBecauseErrorCode(any(), any())
        }
    }

    private fun getHttpClientWithConnectTimeoutAndInterceptors(
        retryInterceptor: RetryInterceptor,
        connectionTimeout: Long
    ): OkHttpClient {
        return defaultHttpClient.fork {
            connectTimeout = TimeUnit.SECONDS.toMillis(connectionTimeout)
            interceptors { +retryInterceptor }
        }
    }

    private fun callGetWithResponseAfterDelay(client: OkHttpClient) {
        httpGet(client = client) {
            host = "postman-echo.com"
            path = "/delay/10"
        }
    }

    private fun createExpectationForGetWithGatewayTimeout(count: Int) {
        MockServerClient(localhost, mockServerPort)
            .`when`(
                request()
                    .withMethod("GET")
                    .withPath("/gatewayTimeout"),
                exactly(count)
            )
            .respond(
                response()
                    .withStatusCode(504)
                    .withBody("")
            )
    }

}