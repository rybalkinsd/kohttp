package io.github.rybalkinsd.kohttp.dsl

import io.github.rybalkinsd.kohttp.assertResponses
import io.github.rybalkinsd.kohttp.util.asJson
import org.junit.Test
import kotlin.test.assertEquals

/**
 * Created by Sergey on 23/07/2018.
 */
class HttpDeleteDslKtTest {

    @Test
    fun `delete request with form # postman echo`() {
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

        httpDelete {
            host = "postman-echo.com"
            path = "/delete"

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
            val parsedResponse = it.body()?.string().asJson()
            assertResponses(parsedResponse["headers"], expectedHeader)
            assertResponses(parsedResponse["args"], expectedParams)
            assertResponses(parsedResponse["form"], expectedForm)
            assertEquals(200, it.code())
        }
    }

    @Test
    fun `delete request with json # postman echo`() {
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

        val response = httpDelete {
            host = "postman-echo.com"
            path = "/delete"

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
            val parsedResponse = it.body()?.string().asJson()
            assertResponses(parsedResponse["headers"], expectedHeader)
            assertResponses(parsedResponse["args"], expectedParams)
            assertResponses(parsedResponse["json"], expectedJson)
            assertEquals(200, it.code())
        }
    }
}