package io.github.rybalkinsd.kohttp.dsl

import io.github.rybalkinsd.kohttp.assertResponses
import io.github.rybalkinsd.kohttp.dsl.async.asyncHttpGet
import io.github.rybalkinsd.kohttp.util.asJson
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals

/**
 * @author sergey
 */
class AsyncHttpGetDslTest {

    @Test
    fun `async http get request`() {
        val expectedHeader = hashMapOf(
                "one" to "42",
                "two" to "123"
        )

        val expectedParams = hashMapOf(
                "text" to "iphone",
                "lr" to "213"
        )

        val response = asyncHttpGet {
            host = "postman-echo.com"
            path = "/get"

            header {
                "one" to 42
                "two" to "123"
            }

            param {
                "text" to "iphone"
                "lr" to 213
            }
        }

        runBlocking {
            response.await().use {
                val parsedResponse = it.body()?.string().asJson()
                assertResponses(parsedResponse["headers"], expectedHeader)
                assertResponses(parsedResponse["args"], expectedParams)
                assertEquals(200, it.code())
            }
        }
    }
}
