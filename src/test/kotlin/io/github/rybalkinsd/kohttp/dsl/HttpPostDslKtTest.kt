package io.github.rybalkinsd.kohttp.dsl

import io.github.rybalkinsd.kohttp.assertResponses
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
            val parsedResponse = it.body()?.string().asJson()
            assertResponses(parsedResponse["headers"], expectedHeader)
            assertResponses(parsedResponse["args"], expectedParams)
            assertResponses(parsedResponse["form"], expectedForm)
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
            val parsedResponse = it.body()?.string().asJson()
            assertResponses(parsedResponse["headers"], expectedHeader)
            assertResponses(parsedResponse["args"], expectedParams)
            assertResponses(parsedResponse["json"], expectedJson)
            assertEquals(200, it.code())
        }
    }

    @Test
    fun `post request with json string # postman echo`() {
        val response = httpPost {
            host = "postman-echo.com"
            path = "/post"

            body {
                "application/json" content
                    """{"login":"user","email":"john.doe@gmail.com"}"""
            }
        }

        response.use {
            println(it.body()?.string())
        }
    }

    @Test
    fun `post request with file # postman echo`() {
        val response = httpPost {
            host = "postman-echo.com"
            path = "/post"

            body {
                "image/gif" content
                    this::class.java.classLoader.getResource("cat.gif").file
            }
        }

        response.use {
            println(it.body()?.string())
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
            val parsedResponse = it.body()?.string().asJson()
            val headers = parsedResponse["headers"]
            assertResponses(headers, expectedHeader)
            assertResponses(parsedResponse["args"], expectedParams)
            assertEquals("0", headers["content-length"].asText(""))
            assertEquals(200, it.code())
        }
    }
}
