package io.github.rybalkinsd.kohttp.performance

import io.github.rybalkinsd.kohttp.dsl.async.httpGetAsync
import io.github.rybalkinsd.kohttp.ext.url
import io.github.rybalkinsd.kohttp.getCode
import io.github.rybalkinsd.kohttp.getSuccessfulResponsesAmount
import io.github.rybalkinsd.kohttp.pool.CoroutineExecutorService
import khttp.responses.Response
import kotlinx.coroutines.*
import org.junit.Test
import java.util.concurrent.ForkJoinPool
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

public class KhttpAsyncHttpGetPerformanceTest {
    private val parallelizm = 256
    private val requestsAmount = 10000
    private val targetUrl = "https://postman-echo.com/get"

    val completed = AtomicInteger(0)
    val failed = AtomicInteger(0)

    @Test
    fun `khttp request through forkJoinPool`() {
        val pool = ForkJoinPool(parallelizm)

        measureTimeMillis {
            repeat(requestsAmount) {
                pool.execute {
                    try {
                        val response = khttp.get(targetUrl)

                        if (response.statusCode == 200) {
                            completed.incrementAndGet()
                        } else {
                            failed.incrementAndGet()
                        }
                    } catch (e: Exception) {
                        failed.incrementAndGet()
                    }
                }
            }
            pool.shutdown()
            pool.awaitTermination(1000, TimeUnit.SECONDS)
        }.also {
            println(completed)
            println(it)
        }
    }

    @Test
    fun `khttp request through kotlin coroutines Dispatchers_IO`() {
        measureTimeMillis {
            val responses = List(requestsAmount) {
                GlobalScope.async(context = Dispatchers.IO) {
                    khttp.get(targetUrl)
                }
            }

            runBlocking {
                responses.map { getCode(it) }
                        .filter { it == 200 }
                        .size
            }.also {
                println(it)
            }
        }.also { print(it) }
    }

    @Test
    fun `khttp request through kotlin coroutines CoroutineExecutorService`() {
        val pool = ForkJoinPool(parallelizm)
        val context = CoroutineExecutorService(pool)

        measureTimeMillis {
            val responses = List(requestsAmount) {
                GlobalScope.async(context = context) {
                    khttp.get(targetUrl)
                }
            }

            runBlocking(context) {
                responses.map { getCode(it) }
                        .filter { it == 200 }
                        .size
            }.also {
                println(it)
            }
        }.also { print(it) }
    }

    private suspend fun getCode(response: Deferred<Response>) =
            try {
                response.await().statusCode
            } catch (_: Throwable) {
                -1
            }
}