package io.github.rybalkinsd.kohttp.dsl.async

import io.github.rybalkinsd.kohttp.dsl.context.Method
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * @author sergey, alex, gokul, hakky54
 */
class HttpAsyncDslTest {

    @Test
    fun `async get request`() {
        val response = httpAsync(method = Method.GET) {
            host = "postman-echo.com"
            path = "/get"
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
    fun `async head request`() {
        val response = httpAsync(method = Method.HEAD) {
            host = "postman-echo.com"
            path = "/get"
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
    fun `async post request`() {
        val response = httpAsync(method = Method.POST) {
            host = "postman-echo.com"
            path = "/post"
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
    fun `async put request`() {
        val response = httpAsync(method = Method.PUT) {
            host = "postman-echo.com"
            path = "/put"
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
    fun `async patch request with param and header`() {
        val response = httpAsync(method = Method.PATCH) {
            host = "postman-echo.com"
            path = "/patch"
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
    fun `async delete request`() {
        val response = httpAsync(method = Method.DELETE) {
            host = "postman-echo.com"
            path = "/delete"
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
