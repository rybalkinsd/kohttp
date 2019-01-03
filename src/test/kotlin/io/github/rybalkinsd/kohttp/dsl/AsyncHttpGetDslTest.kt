package io.github.rybalkinsd.kohttp.dsl

import assertResponses
import com.fasterxml.jackson.databind.ObjectMapper
import io.github.rybalkinsd.kohttp.dsl.async.asyncHttpGet
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals

class AsyncHttpGetDslTest {

    @Test
    fun name() {
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
                val parsedResponse = ObjectMapper().readValue(it.body()?.byteStream(), kotlin.collections.hashMapOf<String, Any>()::class.java)
                assertResponses(parsedResponse["headers"] as LinkedHashMap<String, Any>, expectedHeader)
                assertResponses(parsedResponse["args"] as LinkedHashMap<String, Any>, expectedParams)
                assertEquals(200, it.code())
            }
        }
    }
}
