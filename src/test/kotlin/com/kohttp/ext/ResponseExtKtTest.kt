package com.kohttp.ext

import com.kohttp.dsl.httpGet
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * Created by Sergey Rybalkin on 07/10/2018.
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
        }.plain()

        with(plainResponse) {
            assertEquals(200, code)
            assertEquals(7, headers.size)
            assertNotNull(body)
            assertTrue { body!!.isNotEmpty() }
        }
    }

    @Test
    fun `make plain response from http get response  # ext`() {
        val plainResponse = "https://www.yandex.ru/search/?text=iphone".httpGet().plain()

        with(plainResponse) {
            assertEquals(200, code)
            assertTrue { headers.isNotEmpty() }
            assertNotNull(body)
            assertTrue { body!!.isNotEmpty() }
        }
    }
}