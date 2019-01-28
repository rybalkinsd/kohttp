package io.github.rybalkinsd.kohttp.client.dsl

import io.github.rybalkinsd.kohttp.client.defaultHttpClient
import io.github.rybalkinsd.kohttp.client.fork
import io.github.rybalkinsd.kohttp.ext.httpGet
import okhttp3.Interceptor
import okhttp3.Response
import org.junit.Test
import java.time.Instant
import kotlin.test.assertEquals
import kotlin.test.assertTrue

private class TestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestTime = Instant.now().epochSecond
        val response = chain.proceed(chain.request())
        return with(response.newBuilder())  {
            addHeader("x-response-time", "${Instant.now().epochSecond - requestTime}")
            build()
        }
    }
}

class InterceptorsDslTest {

    @Test
    fun `adds interceptor to client`() {
        val client = defaultHttpClient.fork {
            interceptors {
                + TestInterceptor()
            }
        }

        "https://postman-echo.com/get".httpGet(client = client).use {
            assertTrue { it.header("x-response-time") != null  }
        }
    }

    @Test
    fun `adds interceptors to forked client`() {
        val interceptors = listOf(TestInterceptor(), TestInterceptor(), TestInterceptor())
        val client = defaultHttpClient.newBuilder().addInterceptor(interceptors[0]).build()
        val forkedClient = client.fork {
            interceptors {
                +interceptors[1]
                +interceptors[2]
            }
        }
        forkedClient.interceptors().mapIndexed { idx, it ->
            assertEquals(it, interceptors[idx])
        }
    }
}
