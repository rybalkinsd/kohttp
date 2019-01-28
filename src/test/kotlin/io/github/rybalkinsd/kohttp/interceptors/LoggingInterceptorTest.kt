package io.github.rybalkinsd.kohttp.interceptors

import io.github.rybalkinsd.kohttp.client.defaultHttpClient
import io.github.rybalkinsd.kohttp.client.fork
import io.github.rybalkinsd.kohttp.ext.httpGet
import org.junit.Test
import kotlin.test.assertTrue

class LoggingInterceptorTest {
    @Test
    fun `calls passed logging method with message`() {
        val client = defaultHttpClient.fork {
            interceptors {
                +LoggingInterceptor {
                    assertTrue(it.isNotEmpty())
                }
            }
        }

        "https://postman-echo.com/get".httpGet(client = client)
    }
}
