package io.github.rybalkinsd.kohttp.dsl.async

import io.github.rybalkinsd.kohttp.assertResponses
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
        val expectedHeader = mapOf(
                "one" to "42",
                "two" to "123"
        )

        val expectedParams = mapOf(
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
                assertResponses(expectedHeader, parsedResponse["headers"])
                assertResponses(expectedParams, parsedResponse["args"])
                assertEquals(200, it.code())
            }
        }
    }
}
