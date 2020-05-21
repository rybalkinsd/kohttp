package io.github.rybalkinsd.kohttp.dsl

import io.github.rybalkinsd.kohttp.dsl.context.HttpContext
import io.github.rybalkinsd.kohttp.dsl.context.Method
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * @author sergey, alex, gokul, hakky54
 */
class HttpDslKtTest {

    @Test
    fun `get request`() {
        http(method = Method.GET) {
            host = "postman-echo.com"
            path = "/get"
        }.use {
            assertThat(it.code()).isEqualTo(200)
        }
    }

    @Test
    fun `head request`() {
        http(method = Method.HEAD) {
            host = "postman-echo.com"
            path = "/get"
        }.use {
            assertThat(it.code()).isEqualTo(200)
        }
    }

    @Test
    fun `post request`() {
        val ctx : HttpContext.() -> Unit = {
            host = "postman-echo.com"
            path = "/post"
        }

        http(method = Method.POST, init = ctx)
                .use {
                    assertThat(it.code()).isEqualTo(200)
                }
    }

    @Test
    fun `put request`() {
        http(method = Method.PUT) {
            host = "postman-echo.com"
            path = "/put"
        }.use {
            assertThat(it.code()).isEqualTo(200)
        }
    }

    @Test
    fun `patch request`() {
        http(method = Method.PATCH) {
            host = "postman-echo.com"
            path = "/patch"
        }.use {
            assertThat(it.code()).isEqualTo(200)
        }
    }

    @Test
    fun `delete request with param and header`() {
        http(method = Method.DELETE) {
            host = "postman-echo.com"
            path = "/delete"
        }.use {
            assertThat(it.code()).isEqualTo(200)
        }
    }

}
