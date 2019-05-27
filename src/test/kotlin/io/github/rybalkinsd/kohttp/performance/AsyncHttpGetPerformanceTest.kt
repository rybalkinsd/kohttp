package io.github.rybalkinsd.kohttp.performance

import io.github.rybalkinsd.kohttp.client.client
import io.github.rybalkinsd.kohttp.client.defaultHttpClient
import io.github.rybalkinsd.kohttp.configuration.ConnectionPoolConfig
import io.github.rybalkinsd.kohttp.configuration.config
import io.github.rybalkinsd.kohttp.dsl.async.httpGetAsync
import io.github.rybalkinsd.kohttp.dsl.context.HttpGetContext
import io.github.rybalkinsd.kohttp.dsl.httpGet
import io.github.rybalkinsd.kohttp.ext.url
import kotlinx.coroutines.*
import okhttp3.*
import org.junit.Test
import java.io.IOException
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import kotlin.reflect.full.declaredMembers
import kotlin.reflect.jvm.isAccessible
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
class AsyncHttpGetPerformanceTest {

    /**
     * Running 100 simple requests tu measure current realisation
     */
    @Test
    fun `100 simple async requests with Call-Factory-suspendCall extension`() {
        measureTimeMillis {
            val rs = List(100) {
                httpGetAsync {
                    url("https://postman-echo.com/delay/2")
                }
            }

            runBlocking {
                val code = rs.awaitAll().map { it.code() }
                println(code)
            }
        }.also { print(it) }
    }


    /**
     * Running 100 simple requests tu measure old realisation
     */
    @Test
    fun `100 simple async requests with old Call-Factory-suspendCall extension`() {
        measureTimeMillis {
            val rs = List(100) {
                oldAsyncHttpGet(client = pseudoDefault) {
                    url("https://postman-echo.com/delay/2")
                }
            }

            runBlocking {
                val code = rs.awaitAll().map { it.code() }
                println(code)
            }
        }.also { print(it) }
    }


    /**
     * Running 100 simple requests tu measure realisation with kotlin
     * async with Default dispatcher
     */
    @Test
    fun `100 simple async requests with kotlin coroutines Dispatcher-Default`() {
        measureTimeMillis {
            val rs = List(100) {
                GlobalScope.async(context = Dispatchers.Default) {
                    httpGet {
                        url("https://postman-echo.com/delay/2")
                    }
                }
            }

            runBlocking {
                val code = rs.awaitAll().map { it.code() }
                println(code)
            }
        }.also { print(it) }
    }


    /**
     * Running 100 simple requests tu measure realisation with kotlin
     * async with IO dispatcher
     */
    @Test
    fun `100 simple async requests with kotlin coroutines Dispatcher-IO`() {
        measureTimeMillis {
            val rs = List(100) {
                GlobalScope.async(context = Dispatchers.IO) {
                    httpGet {
                        url("https://postman-echo.com/delay/2")
                    }
                }
            }

            runBlocking {
                val code = rs.awaitAll().map { it.code() }
                println(code)
            }
        }.also { print(it) }
    }

    @Test
    fun `Check dispatcher`() {
        val cd = Dispatchers.IO is Executor
        println(cd)
        println(Dispatchers.IO::class.java.name)
        println(Runtime.getRuntime().availableProcessors())
        println(Dispatchers.IO::class.declaredMembers
                .firstOrNull { it.name == "parallelism" }
                ?.also { it.isAccessible = true }
                ?.call(Dispatchers.IO)
        )
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
            dispatcher = Dispatcher().also {
                it.maxRequestsPerHost = 1000
                it.maxRequests = 1000
            }
        }
    }

    private fun ConnectionPoolConfig.create() = ConnectionPool(100, keepAliveDuration, TimeUnit.MILLISECONDS)
}