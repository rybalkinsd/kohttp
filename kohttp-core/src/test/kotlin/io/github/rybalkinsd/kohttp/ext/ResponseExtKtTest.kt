package io.github.rybalkinsd.kohttp.ext

import io.github.rybalkinsd.kohttp.dsl.httpGet
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

/**
 * @author sergey
 */
class ResponseExtKtTest {

    private val getUrl = "https://postman-echo.com/get"

    @Test
    fun `make plain response from http get response # dsl`() {
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
        val response = getUrl.httpGet().asString()!!
        val expectedRegex = """{
            |"args":{},
            |"headers":{
            |   "x-forwarded-proto":"https",
            |   "host":"postman-echo.com",
            |   "accept-encoding":"gzip",
            |   "user-agent":"okhttp/[0-9]*.[0-9]*.[0-9]*",
            |   "x-forwarded-port":"443"
            |   },
            |"url":"https://postman-echo.com/get"
            |}"""
                .trimMargin("|")
                .replace(Regex("\\s"),"")
                .escape()


        assertThat(response).matches(expectedRegex)
    }

    @Test
    fun `streams response # ext`() {
        val response = "https://postman-echo.com/stream/2".httpGet().asStream()
                ?.readBytes()?.let { String(it) }

        val expectedRegex = """{
        |  "args": {
        |    "n": "2"
        |  },
        |  "headers": {
        |    "x-forwarded-proto": "https",
        |    "host": "postman-echo.com",
        |    "accept-encoding": "gzip",
        |    "user-agent": "okhttp/[0-9]*.[0-9]*.[0-9]*",
        |    "x-forwarded-port": "443"
        |  },
        |  "url": "https://postman-echo.com/stream/2"
        |}{
        |  "args": {
        |    "n": "2"
        |  },
        |  "headers": {
        |    "x-forwarded-proto": "https",
        |    "host": "postman-echo.com",
        |    "accept-encoding": "gzip",
        |    "user-agent": "okhttp/[0-9]*.[0-9]*.[0-9*]",
        |    "x-forwarded-port": "443"
        |  },
        |  "url": "https://postman-echo.com/stream/2"
        |}"""
                .trimMargin("|")
                .escape()

        assertThat(response).matches(expectedRegex)
    }
}

private fun String.escape(): String = replace("/", "\\/")
        .replace("{", "\\{")
        .replace("}", "\\}")
