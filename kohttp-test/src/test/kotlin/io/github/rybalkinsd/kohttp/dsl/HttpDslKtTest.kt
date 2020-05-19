package io.github.rybalkinsd.kohttp.dsl

import io.github.rybalkinsd.kohttp.assertContainsAtLeast
import io.github.rybalkinsd.kohttp.assertContainsExactly
import io.github.rybalkinsd.kohttp.client.defaultHttpClient
import io.github.rybalkinsd.kohttp.client.fork
import io.github.rybalkinsd.kohttp.dsl.context.HttpContext
import io.github.rybalkinsd.kohttp.dsl.context.Method
import io.github.rybalkinsd.kohttp.interceptors.logging.HttpLoggingInterceptor
import io.github.rybalkinsd.kohttp.jackson.ext.toJson
import io.github.rybalkinsd.kohttp.util.json
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatCode
import org.junit.Test

class HttpDslKtTest {

    @Test
    fun `post request with param and header and body`() {
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

        val client = defaultHttpClient.fork {
            interceptors {
                +HttpLoggingInterceptor()
            }
        }

        http(client, method = Method.POST, init = fun HttpContext.() {
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
        }).use {
            val parsedResponse = it.toJson()
            assertContainsAtLeast(expectedHeader, parsedResponse["headers"])
            assertContainsExactly(expectedParams, parsedResponse["args"])
            assertContainsExactly(expectedForm, parsedResponse["form"])
            assertThat(it.code()).isEqualTo(200)
        }
    }

    @Test
    fun `get request with param and header`() {
        val variable = 123L
        val expectedHeader = mapOf(
                "one" to "42",
                "two" to variable.toString(),
                "three" to """{"a":$variable,"b":{"b1":"512"},"c":[1,2.0,3]}""",
                "cookie" to "aaa=bbb; ccc=42"
        )

        val expectedParams = mapOf(
                "text" to "iphone",
                "lr" to "213"
        )

        val response = http(method = Method.GET) {
            host = "postman-echo.com"
            path = "/get"

            header {
                "one" to 42
                "two" to variable
                "three" to json {
                    "a" to variable
                    "b" to json {
                        "b1" to "512"
                    }
                    "c" to listOf(1, 2.0, 3)
                }

                cookie {
                    "aaa" to "bbb"
                    "ccc" to 42
                }
            }

            param {
                "text" to "iphone"
                "lr" to 213
            }
        }
        response.use {
            val parsedResponse = it.toJson()
            assertContainsAtLeast(expectedHeader, parsedResponse["headers"])
            assertContainsExactly(expectedParams, parsedResponse["args"])
            assertThat(it.code()).isEqualTo(200)
        }
    }

    @Test
    fun `get request with body throws exception`() {
        val variable = 123L

        assertThatCode {
            http(method = Method.GET) {
                host = "postman-echo.com"
                path = "/get"

                header {
                    "one" to 42
                    "two" to variable
                    "three" to json {
                        "a" to variable
                        "b" to json {
                            "b1" to "512"
                        }
                        "c" to listOf(1, 2.0, 3)
                    }

                    cookie {
                        "aaa" to "bbb"
                        "ccc" to 42
                    }
                }

                param {
                    "text" to "iphone"
                    "lr" to 213
                }

                body {
                    form {
                        "login" to "user"
                        "email" to "john.doe@gmail.com"
                    }
                }
            }
        }.isInstanceOf(UnsupportedOperationException::class.java)
         .hasMessage("Request body is not supported for [GET] Method.")
    }

    @Test
    fun `delete request with param and header`() {
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

        http(method = Method.DELETE, init = fun HttpContext.() {
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
        }).use {
            val parsedResponse = it.toJson()
            assertContainsAtLeast(expectedHeader, parsedResponse["headers"])
            assertContainsExactly(expectedParams, parsedResponse["args"])
            assertContainsExactly(expectedForm, parsedResponse["form"])
            assertThat(it.code()).isEqualTo(200)
        }
    }

}
