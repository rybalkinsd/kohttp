package io.github.rybalkinsd.kohttp.dsl

import assertResponses
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Test
import kotlin.test.assertEquals

/**
 * Created by Sergey on 23/07/2018.
 */
class HttpPutDslKtTest {

    @Test
    fun `put request with form # postman echo`() {
        val expectedHeader = hashMapOf(
                "one" to "42",
                "cookie" to "aaa=bbb; ccc=42"
        )

        val expectedParams = hashMapOf(
                "arg" to "iphone"
        )

        val expectedForm = hashMapOf(
                "login" to "user",
                "email" to "john.doe@gmail.com"
        )

        httpPut {
            host = "postman-echo.com"
            path = "/put"

            param {
                "arg" to "iphone"
            }

            header {
                "one" to 42
                cookie {
                    "aaa" to "bbb"
                    "ccc" to 42
                }
            }

            body {
                form {
                    "login" to "user"
                    "email" to "john.doe@gmail.com"
                }
            }
        }.use {
            val parsedResponse = ObjectMapper().readValue(it.body()?.byteStream(), kotlin.collections.hashMapOf<String, Any>()::class.java)
            assertResponses(parsedResponse["headers"] as LinkedHashMap<String, Any>, expectedHeader)
            assertResponses(parsedResponse["args"] as LinkedHashMap<String, Any>, expectedParams)
            assertResponses(parsedResponse["form"] as LinkedHashMap<String, Any>, expectedForm)
            assertEquals(200, it.code())
        }
    }

    @Test
    fun `put request with json # postman echo`() {
        val expectedHeader = hashMapOf(
                "one" to "42",
                "cookie" to "aaa=bbb; ccc=42"
        )

        val expectedParams = hashMapOf(
                "arg" to "iphone"
        )

        val expectedJson = hashMapOf(
                "login" to "user",
                "email" to "john.doe@gmail.com"
        )
        val response = httpPut {
            host = "postman-echo.com"
            path = "/put"

            param {
                "arg" to "iphone"
            }

            header {
                "one" to 42
                cookie {
                    "aaa" to "bbb"
                    "ccc" to 42
                }
            }

            body {
                json {
                    "login" to "user"
                    "email" to "john.doe@gmail.com"
                }
            }
        }

        response.use {
            val parsedResponse = ObjectMapper().readValue(it.body()?.byteStream(), kotlin.collections.hashMapOf<String, Any>()::class.java)
            assertResponses(parsedResponse["headers"] as LinkedHashMap<String, Any>, expectedHeader)
            assertResponses(parsedResponse["args"] as LinkedHashMap<String, Any>, expectedParams)
            assertResponses(parsedResponse["json"] as LinkedHashMap<String, Any>, expectedJson)
            assertEquals(200, it.code())
        }
    }
}