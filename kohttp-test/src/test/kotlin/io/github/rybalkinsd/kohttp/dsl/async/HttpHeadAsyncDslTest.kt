package io.github.rybalkinsd.kohttp.dsl.async

import io.github.rybalkinsd.kohttp.dsl.context.Method
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class HttpHeadAsyncDslTest {

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

}
