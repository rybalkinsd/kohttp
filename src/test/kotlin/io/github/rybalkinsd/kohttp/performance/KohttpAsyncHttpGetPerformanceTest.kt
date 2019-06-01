package io.github.rybalkinsd.kohttp.performance

import io.github.rybalkinsd.kohttp.client.client
import io.github.rybalkinsd.kohttp.client.defaultHttpClient
import io.github.rybalkinsd.kohttp.configuration.ConnectionPoolConfig
import io.github.rybalkinsd.kohttp.configuration.config
import io.github.rybalkinsd.kohttp.dsl.async.httpGetAsync
import io.github.rybalkinsd.kohttp.dsl.context.HttpGetContext
import io.github.rybalkinsd.kohttp.dsl.httpGet
import io.github.rybalkinsd.kohttp.ext.url
import io.github.rybalkinsd.kohttp.getSuccessfulResponsesAmount
import io.github.rybalkinsd.kohttp.pool.CoroutineExecutorService
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import okhttp3.*
import org.junit.Test
import java.io.IOException
import java.util.concurrent.ForkJoinPool
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import kotlin.system.measureTimeMillis


/**
 * Compare performance of different execution variants
 *
 * Test proposal - run 100 simple get requests
 * to measure time that will be spent on execution.
 * Compare result with another alternatives
 * that also presented in this class
 *
 * Requests' statuses and time in millis will be written in console
 * @since 0.10.0
 * @author evgeny
 */
class KohttpAsyncHttpGetPerformanceTest {
    private val requestsAmount = 10000
    private val targetUrl = "https://postman-echo.com/get"
    private val parallelizm = 256


    /**
     * Running 100 simple requests tu measure current realisation
     */
    @Test
    fun `1000 simple async requests with Call-Factory-suspendCall extension`() {
        measureTimeMillis {
            val responses = List(requestsAmount) {
                httpGetAsync {
                    url(targetUrl)
                }
            }

            println(getSuccessfulResponsesAmount(responses))
        }.also { print(it) }
    }


    /**
     * Running 100 simple requests tu measure old realisation
     */
    @Test
    fun `10000 simple async requests with old Call-Factory-suspendCall extension`() {
        measureTimeMillis {
            val responses = List(requestsAmount) {
                oldAsyncHttpGet(client = pseudoDefault) {
                    url(targetUrl)
                }
            }

            println(getSuccessfulResponsesAmount(responses))
        }.also { print(it) }
    }


    /**
     * Running 100 simple requests tu measure realisation with kotlin
     * async with Default dispatcher
     */
    @Test
    fun `10000 simple async requests with kotlin coroutines Dispatcher-Default`() {
        measureTimeMillis {
            val responses = List(requestsAmount) {
                GlobalScope.async(context = Dispatchers.Default) {
                    httpGet {
                        url(targetUrl)
                    }
                }
            }

            println(getSuccessfulResponsesAmount(responses))
        }.also { print(it) }
    }


    /**
     * Running 100 simple requests tu measure realisation with kotlin
     * async with IO dispatcher
     */
    @Test
    fun `10000 simple async requests with kotlin coroutines Dispatcher-IO`() {
        measureTimeMillis {
            val responses = List(requestsAmount) {
                GlobalScope.async(context = Dispatchers.IO) {
                    httpGet {
                        url(targetUrl)
                    }
                }
            }

            println(getSuccessfulResponsesAmount(responses))
        }.also { print(it) }
    }


    /**
     * Running 100 simple requests tu measure realisation with kotlin
     * async with IO dispatcher
     */
    @Test
    fun `10000 simple async requests with custom coroutines Dispatcher`() {
        val pool = ForkJoinPool(parallelizm)
        val context = CoroutineExecutorService(pool)

        measureTimeMillis {
            val responses = List(requestsAmount) {
                GlobalScope.async(context = context) {
                    httpGet {
                        url(targetUrl)
                    }
                }
            }

            println(getSuccessfulResponsesAmount(responses))
        }.also { print(it) }
    }

    companion object {
        /**
         * Old async httpGet method, before 0.10.0
         */
        private fun oldAsyncHttpGet(
                client: Call.Factory = defaultHttpClient,
                init: HttpGetContext.() -> Unit
        ): Deferred<Response> = GlobalScope.async(context = Dispatchers.IO) {
            client.oldSuspendCall(HttpGetContext().apply(init).makeRequest())
        }

        private suspend fun Call.Factory.oldSuspendCall(
                request: Request
        ): Response = suspendCoroutine { cont ->
            newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    cont.resume(response)
                }

                override fun onFailure(call: Call, e: IOException) {
                    cont.resumeWithException(e)
                }
            })
        }
    }

    val pseudoDefault: OkHttpClient = config.client.let {
        client {
            connectionPool = it.connectionPoolConfig.create()
            connectTimeout = it.connectTimeout
            readTimeout = it.readTimeout
            writeTimeout = it.writeTimeout
            followRedirects = it.followRedirects
            followSslRedirects = it.followSslRedirects
            dispatcher = Dispatcher(ForkJoinPool(parallelizm)).also {
                it.maxRequestsPerHost = 256
                it.maxRequests = 256
            }
        }
    }

    private fun ConnectionPoolConfig.create() = ConnectionPool(100, keepAliveDuration, TimeUnit.MILLISECONDS)
}