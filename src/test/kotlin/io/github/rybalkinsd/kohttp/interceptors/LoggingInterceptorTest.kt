package io.github.rybalkinsd.kohttp.interceptors

import io.github.rybalkinsd.kohttp.client.defaultHttpClient
import io.github.rybalkinsd.kohttp.client.fork
import io.github.rybalkinsd.kohttp.dsl.upload
import io.github.rybalkinsd.kohttp.ext.httpGet
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class LoggingInterceptorTest {
    @Test
    fun `calls passed logging method with message`() {
        var logSize = 0
        val client = defaultHttpClient.fork {
            interceptors {
                +LoggingInterceptor {
                    logSize += it.length
                }
            }
        }

        "https://postman-echo.com/get".httpGet(client)

        assertTrue { logSize > 0 }
    }

    @Test
    fun `default logging happens without exceptions`() {
        val client = defaultHttpClient.fork {
            interceptors {
                +LoggingInterceptor()
            }
        }

        val response = "https://postman-echo.com/get".httpGet(client)

        assertEquals(200, response.code())
    }

    @Test
    fun `default logging happens without exceptions when we have response body`() {
        val client = defaultHttpClient.fork {
            interceptors {
                +LoggingInterceptor()
            }
        }

        val fileUri = this.javaClass.getResource("/cat.gif").toURI()
        val response = upload(client) {
            url("http://postman-echo.com/post")
            file(fileUri)
        }

        assertEquals(200, response.code())
    }

    @Test
    fun `curl command logging happens without exceptions `() {
        val client = defaultHttpClient.fork {
            interceptors {
                +LoggingInterceptor(LoggingFormat.CURL)
            }
        }

        val response = "https://postman-echo.com/get".httpGet(client)

        assertEquals(200, response.code())
    }
}
