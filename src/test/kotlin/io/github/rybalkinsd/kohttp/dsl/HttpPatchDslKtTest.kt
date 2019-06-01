package io.github.rybalkinsd.kohttp.dsl

import io.github.rybalkinsd.kohttp.assertResponses
import io.github.rybalkinsd.kohttp.util.asJson
import org.junit.Test
import kotlin.test.assertEquals

/**
 * @author bpaxio, gokul
 */
class HttpPatchDslKtTest {

    @Test
    fun `patch request with form # postman echo`() {
        val expectedHeader = mapOf(
            "one" to "42",
            "cookie" to "aaa=bbb; ccc=42"
        )

        val expectedParams = mapOf(
            "arg" to "iphone"
        )

        val expectedForm = mapOf(
            "login" to "user",
            "email" to "john.doe@gmail.com"
        )

        httpPatch {
            host = "postman-echo.com"
            path = "/patch"

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
            assertResponses(expectedHeader, parsedResponse["headers"])
            assertResponses(expectedParams, parsedResponse["args"])
            assertResponses(expectedForm, parsedResponse["form"])
            assertEquals(200, it.code())
        }
    }

    @Test
    fun `patch request with json # postman echo`() {
        val expectedHeader = mapOf(
            "one" to "42",
            "cookie" to "aaa=bbb; ccc=42"
        )

        val expectedParams = mapOf(
            "arg" to "iphone"
        )

        val expectedJson = mapOf(
            "login" to "user",
            "email" to "john.doe@gmail.com"
        )

        val response = httpPatch {
            host = "postman-echo.com"
            path = "/patch"

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
            assertResponses(expectedHeader, parsedResponse["headers"])
            assertResponses(expectedParams, parsedResponse["args"])
            assertResponses(expectedJson, parsedResponse["json"])
            assertEquals(200, it.code())
        }
    }
}