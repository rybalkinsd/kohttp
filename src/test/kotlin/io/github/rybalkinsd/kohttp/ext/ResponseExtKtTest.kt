package io.github.rybalkinsd.kohttp.ext

import io.github.rybalkinsd.kohttp.dsl.httpGet
import io.github.rybalkinsd.kohttp.util.json
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

/**
 * @author sergey
 */
class ResponseExtKtTest {

    @Test
    fun `make plain response from http get response  # dsl`() {
        val plainResponse = httpGet {
            host = "postman-echo.com"
            path = "/get"

            header {
                "one" to 42
                "two" to 123L
            }
        }.eager()

        with(plainResponse) {
            assertEquals(200, code)
            assertEquals(7, headers.size)
            assertNotNull(body)
            assertTrue { body!!.isNotEmpty() }
            assertEquals("GET", request.method())
            assertEquals("HTTP_1_1", protocol.name)
            assertEquals("OK", message)
            assertNotNull(networkResponse)
            assertNull(cacheResponse)
            assertNull(priorResponse)
            assertTrue { sentRequestAtMillis < receivedResponseAtMillis }
        }
    }

    @Test
    fun `make plain response from http get response  # ext`() {
        val plainResponse = "https://www.yandex.ru/search/?text=iphone".httpGet().eager()

        with(plainResponse) {
            assertEquals(200, code)
            assertTrue { headers.isNotEmpty() }
            assertNotNull(body)
            assertTrue { body!!.isNotEmpty() }
        }
    }

    @Test
    fun `gets response as string # ext`() {
        val response = "https://postman-echo.com/get".httpGet().asString()
        val expected = "{\"args\":{},\"headers\":{\"x-forwarded-proto\":\"https\",\"host\":\"postman-echo.com\",\"accept-encoding\":\"gzip\",\"user-agent\":\"okhttp/3.12.0\",\"x-forwarded-port\":\"443\"},\"url\":\"https://postman-echo.com/get\"}"
        assertEquals(expected, response)
    }

    @Test
    fun `gets response as json # ext`() {
        val response = "https://postman-echo.com/get".httpGet().asJson()
        val expected = json {
            "args" to json { }
            "headers" to json {
                "x-forwarded-proto" to "https"
                "host" to "postman-echo.com"
                "accept-encoding" to "gzip"
                "user-agent" to "okhttp/3.12.0"
                "x-forwarded-port" to "443"
            }
            "url" to "https://postman-echo.com/get"
        }.asJson()
        assertEquals(response, expected)
    }
}