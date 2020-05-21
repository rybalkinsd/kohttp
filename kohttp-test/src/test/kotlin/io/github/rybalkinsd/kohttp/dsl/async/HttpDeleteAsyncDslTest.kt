package io.github.rybalkinsd.kohttp.dsl.async

import io.github.rybalkinsd.kohttp.assertContainsAtLeast
import io.github.rybalkinsd.kohttp.assertContainsExactly
import io.github.rybalkinsd.kohttp.jackson.ext.toJson
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * @author sergey, alex, gokul, hakky54
 */
class HttpDeleteAsyncDslTest {

    @Test
    fun `async delete request with param and header and body`() {
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

        val response = httpDeleteAsync {
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
        }

        assertThat(response.isCompleted)
                .`as`("After coroutine call, we must have not-ready response")
                .isFalse()

        runBlocking {
            response.await().use {
                val parsedResponse = it.toJson()
                assertContainsAtLeast(expectedHeader, parsedResponse["headers"])
                assertContainsExactly(expectedParams, parsedResponse["args"])
                assertContainsExactly(expectedForm, parsedResponse["form"])
                assertThat(it.code()).isEqualTo(200)
            }
        }
    }

}