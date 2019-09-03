package io.github.rybalkinsd.kohttp.dsl

import io.github.rybalkinsd.kohttp.jackson.ext.toJson
import io.github.rybalkinsd.kohttp.assertContainsAtLeast
import io.github.rybalkinsd.kohttp.assertContainsExactly
import org.junit.Test
import kotlin.test.assertEquals

/**
 * @author sergey, gokul
 */
class HttpDeleteDslKtTest {

    @Test
    fun `delete request with form # postman echo`() {
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
            val parsedResponse = it.toJson()
            assertContainsAtLeast(expectedHeader, parsedResponse["headers"])
            assertContainsExactly(expectedParams, parsedResponse["args"])
            assertContainsExactly(expectedForm, parsedResponse["form"])
            assertEquals(200, it.code())
        }
    }

    @Test
    fun `delete request with json # postman echo`() {
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
            val parsedResponse = it.toJson()
            assertContainsAtLeast(expectedHeader, parsedResponse["headers"])
            assertContainsExactly(expectedParams, parsedResponse["args"])
            assertContainsExactly(expectedJson, parsedResponse["json"])
            assertEquals(200, it.code())
        }
    }
}