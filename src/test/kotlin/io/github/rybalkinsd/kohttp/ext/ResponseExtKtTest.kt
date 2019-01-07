package io.github.rybalkinsd.kohttp.ext

import io.github.rybalkinsd.kohttp.dsl.httpGet
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
}