package io.github.rybalkinsd.kohttp.dsl.async

import io.github.rybalkinsd.kohttp.assertContainsAtLeast
import io.github.rybalkinsd.kohttp.assertContainsExactly
import io.github.rybalkinsd.kohttp.dsl.context.Method
import io.github.rybalkinsd.kohttp.jackson.ext.toJson
import io.github.rybalkinsd.kohttp.util.json
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * @author sergey, alex, gokul, hakky54
 */
class HttpAsyncDslTest {

    @Test
    fun `async get request with param and header`() {
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

        val response = httpAsync(method = Method.GET) {
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

        assertThat(response.isCompleted)
                .`as`("After coroutine call, we must have not-ready response")
                .isFalse()

        runBlocking {
            response.await().use {
                val parsedResponse = it.toJson()
                assertContainsAtLeast(expectedHeader, parsedResponse["headers"])
                assertContainsExactly(expectedParams, parsedResponse["args"])
                assertThat(it.code()).isEqualTo(200)
            }
        }
    }

    @Test
    fun `async head request with param and header`() {
        val response = httpAsync(method = Method.HEAD) {
            host = "postman-echo.com"
            path = "/get"

            param {
                "text" to "iphone"
                "lr" to 213
            }
        }

        assertThat(response.isCompleted)
                .`as`("After coroutine call, we must have not-ready response")
                .isFalse()

        runBlocking {
            response.await().use {
                assertThat(it.code()).isEqualTo(200)
            }
        }
    }

    @Test
    fun `async post request with param and header`() {
        val expectedHeader = mapOf(
                "one" to "42",
                "cookie" to "aaa=bbb; ccc=42"
        )

        val expectedParams = mapOf(
                "arg" to "iphone"
        )

        val response = httpAsync(method = Method.POST) {
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

        assertThat(response.isCompleted)
                .`as`("After coroutine call, we must have not-ready response")
                .isFalse()

        runBlocking {
            response.await().use {
                val parsedResponse = it.toJson()
                assertContainsAtLeast(expectedHeader, parsedResponse["headers"])
                assertContainsExactly(expectedParams, parsedResponse["args"])
                assertThat(it.code()).isEqualTo(200)
            }
        }
    }

    @Test
    fun `async put request with param and header`() {
        val expectedHeader = mapOf(
                "one" to "42",
                "cookie" to "aaa=bbb; ccc=42"
        )

        val expectedParams = mapOf(
                "arg" to "iphone"
        )

        val response = httpAsync(method = Method.PUT) {
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
        }

        assertThat(response.isCompleted)
                .`as`("After coroutine call, we must have not-ready response")
                .isFalse()

        runBlocking {
            response.await().use {
                val parsedResponse = it.toJson()
                assertContainsAtLeast(expectedHeader, parsedResponse["headers"])
                assertContainsExactly(expectedParams, parsedResponse["args"])
                assertThat(it.code()).isEqualTo(200)
            }
        }
    }

    @Test
    fun `async patch request with param and header`() {
        val expectedHeader = mapOf(
                "one" to "42",
                "cookie" to "aaa=bbb; ccc=42"
        )

        val expectedParams = mapOf(
                "arg" to "iphone"
        )

        val response = httpAsync(method = Method.PATCH) {
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
        }

        assertThat(response.isCompleted)
                .`as`("After coroutine call, we must have not-ready response")
                .isFalse()

        runBlocking {
            response.await().use {
                val parsedResponse = it.toJson()
                assertContainsAtLeast(expectedHeader, parsedResponse["headers"])
                assertContainsExactly(expectedParams, parsedResponse["args"])
                assertThat(it.code()).isEqualTo(200)
            }
        }
    }

    @Test
    fun `async delete request with param and header`() {
        val expectedHeader = mapOf(
                "one" to "42",
                "cookie" to "aaa=bbb; ccc=42"
        )

        val expectedParams = mapOf(
                "arg" to "iphone"
        )

        val response = httpAsync(method = Method.DELETE) {
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
        }

        assertThat(response.isCompleted)
                .`as`("After coroutine call, we must have not-ready response")
                .isFalse()

        runBlocking {
            response.await().use {
                val parsedResponse = it.toJson()
                assertContainsAtLeast(expectedHeader, parsedResponse["headers"])
                assertContainsExactly(expectedParams, parsedResponse["args"])
                assertThat(it.code()).isEqualTo(200)
            }
        }
    }

}
