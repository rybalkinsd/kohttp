package io.github.rybalkinsd.kohttp.dsl

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.rybalkinsd.kohttp.util.asJson
import org.junit.Test
import kotlin.test.assertEquals

/**
 * Created by Sergey on 23/07/2018.
 */
class HttpPostDslKtTest {

    @Test
    fun `post request with form # postman echo`() {
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
        httpPost {
            host = "postman-echo.com"
            path = "/post"

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
            val headers: LinkedHashMap<String, Any> = parsedResponse["headers"] as LinkedHashMap<String, Any>
            expectedHeader.forEach { k, v ->
                assertEquals(v, headers[k])
            }
            val parameters: LinkedHashMap<String, Any> = parsedResponse["args"] as LinkedHashMap<String, Any>
            expectedParams.forEach { k, v ->
                assertEquals(v, parameters[k])
            }
            val form: LinkedHashMap<String, Any> = parsedResponse["form"] as LinkedHashMap<String, Any>
            expectedForm.forEach { k, v ->
                assertEquals(v, form[k])
            }
            assertEquals(200, it.code())
        }
    }

    @Test
    fun `post request with json # postman echo`() {
        val expectedHeader = hashMapOf(
                "one" to "42",
                "cookie" to "aaa=bbb; ccc=42"
        )

        val expectedParams = hashMapOf(
                "arg" to "iphone"
        )

        val expectedJson= hashMapOf(
                "login" to "user",
                "email" to "john.doe@gmail.com"
        )
        val response = httpPost {
            host = "postman-echo.com"
            path = "/post"

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
            val headers: LinkedHashMap<String, Any> = parsedResponse["headers"] as LinkedHashMap<String, Any>
            expectedHeader.forEach { k, v ->
                assertEquals(v, headers[k])
            }
            val parameters: LinkedHashMap<String, Any> = parsedResponse["args"] as LinkedHashMap<String, Any>
            expectedParams.forEach { k, v ->
                assertEquals(v, parameters[k])
            }
            val json: LinkedHashMap<String, Any> = parsedResponse["json"] as LinkedHashMap<String, Any>
            expectedJson.forEach { k, v ->
                assertEquals(v, json[k])
            }
            assertEquals(200, it.code())
        }
    }

    @Test
    fun `post request with empty body# postman echo`() {
        val expectedHeader = hashMapOf(
                "one" to "42",
                "cookie" to "aaa=bbb; ccc=42"
        )

        val expectedParams = hashMapOf(
                "arg" to "iphone"
        )
        httpPost {
            host = "postman-echo.com"
            path = "/post"

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
        }.use {
            val parsedResponse = ObjectMapper().readValue(it.body()?.byteStream(), kotlin.collections.hashMapOf<String, Any>()::class.java)
            val headers: LinkedHashMap<String, Any> = parsedResponse["headers"] as LinkedHashMap<String, Any>
            expectedHeader.forEach { k, v ->
                assertEquals(v, headers[k])
            }
            val parameters: LinkedHashMap<String, Any> = parsedResponse["args"] as LinkedHashMap<String, Any>
            expectedParams.forEach { k, v ->
                assertEquals(v, parameters[k])
            }
            assertEquals("0", headers["content-length"])
            assertEquals(200, it.code())
        }
    }
}