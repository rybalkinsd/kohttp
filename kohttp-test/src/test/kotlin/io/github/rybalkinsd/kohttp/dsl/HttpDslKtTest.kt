package io.github.rybalkinsd.kohttp.dsl

import io.github.rybalkinsd.kohttp.assertContainsAtLeast
import io.github.rybalkinsd.kohttp.assertContainsExactly
import io.github.rybalkinsd.kohttp.dsl.context.HttpContext
import io.github.rybalkinsd.kohttp.dsl.context.Method
import io.github.rybalkinsd.kohttp.jackson.ext.toJson
import io.github.rybalkinsd.kohttp.util.json
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * @author sergey, alex, gokul, hakky54
 */
class HttpDslKtTest {

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
        }.use {
            val parsedResponse = it.toJson()
            assertContainsAtLeast(expectedHeader, parsedResponse["headers"])
            assertContainsExactly(expectedParams, parsedResponse["args"])
            assertThat(it.code()).isEqualTo(200)
        }
    }

    @Test
    fun `head request with param and header`() {
        http(method = Method.HEAD) {
            host = "postman-echo.com"
            path = "/get"

            param {
                "text" to "iphone"
                "lr" to 213
            }
        }.use {
            assertThat(it.code()).isEqualTo(200)
        }
    }

    @Test
    fun `post request with param and header`() {
        val expectedHeader = mapOf(
            "one" to "42",
            "cookie" to "aaa=bbb; ccc=42"
        )

        val expectedParams = mapOf(
            "arg" to "iphone"
        )

        val ctx : HttpContext.() -> Unit = {
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
        }

        http(method = Method.POST, init = ctx)
                .use {
                    val parsedResponse = it.toJson()
                    assertContainsAtLeast(expectedHeader, parsedResponse["headers"])
                    assertContainsExactly(expectedParams, parsedResponse["args"])
                    assertThat(it.code()).isEqualTo(200)
                }
    }

    @Test
    fun `put request with param and header`() {
        val expectedHeader = mapOf(
                "one" to "42",
                "cookie" to "aaa=bbb; ccc=42"
        )

        val expectedParams = mapOf(
                "arg" to "iphone"
        )

        http(method = Method.PUT) {
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
        }.use {
            val parsedResponse = it.toJson()
            assertContainsAtLeast(expectedHeader, parsedResponse["headers"])
            assertContainsExactly(expectedParams, parsedResponse["args"])
            assertThat(it.code()).isEqualTo(200)
        }
    }

    @Test
    fun `patch request with param and header`() {
        val expectedHeader = mapOf(
                "one" to "42",
                "cookie" to "aaa=bbb; ccc=42"
        )

        val expectedParams = mapOf(
                "arg" to "iphone"
        )

        http(method = Method.PATCH) {
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
        }.use {
            val parsedResponse = it.toJson()
            assertContainsAtLeast(expectedHeader, parsedResponse["headers"])
            assertContainsExactly(expectedParams, parsedResponse["args"])
            assertThat(it.code()).isEqualTo(200)
        }
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

        http(method = Method.DELETE) {
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
        }.use {
            val parsedResponse = it.toJson()
            assertContainsAtLeast(expectedHeader, parsedResponse["headers"])
            assertContainsExactly(expectedParams, parsedResponse["args"])
            assertThat(it.code()).isEqualTo(200)
        }
    }

}
