package io.github.rybalkinsd.kohttp.interceptors.logging

import io.github.rybalkinsd.kohttp.dsl.context.HttpGetContext
import io.github.rybalkinsd.kohttp.dsl.context.HttpPostContext
import io.github.rybalkinsd.kohttp.ext.url
import io.mockk.every
import io.mockk.mockk
import okhttp3.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Sergey Rybalkin on 2019-09-11.
 */
class HttpLoggingInterceptorTest {

    @Test
    fun `log simple get request`() {
        val chain = setupMockChain {
            url("https://postman-echo.com/get")
        }

        val result = chain.test()

        assertThat(result).matches(
            """
                --> $UUID_REGEX
                GET https://postman-echo.com/get http/1.1
                
                ---
                <-- $UUID_REGEX in [0-9]+ ms
                200 http://127.0.0.1/
                a b
                ---

            """.trimIndent()
        )
    }

    @Test
    fun `log of request with header`() {
        val chain = setupMockChain {
            url("https://postman-echo.com/get")
            header {
                cookie {
                    "foo" to 6
                    "bar" to 28
                }
                "Content-Type" to "application/json"
            }
        }

        val result = chain.test()

        assertThat(result).matches(
            """
                --> $UUID_REGEX
                GET https://postman-echo.com/get http/1.1
                cookie foo=6; bar=28
                Content-Type application/json

                ---
                <-- $UUID_REGEX in [0-9]+ ms
                200 http://127.0.0.1/
                a b
                ---

            """.trimIndent()
        )
    }

    @Test
    fun `log request with body`() {
        val chain = setupPostMockChain {
            url("https://postman-echo.com/post")
            body {
                form {
                    "foo" to 123
                    "bar" to "abc"
                }
            }
        }

        val result = chain.test()

        assertThat(result).matches(
            """
                --> $UUID_REGEX
                POST https://postman-echo.com/post http/1.1

                foo=123&bar=abc
                ---
                <-- $UUID_REGEX in [0-9]+ ms
                200 http://127.0.0.1/
                a b
                ---

            """.trimIndent()
        )
    }

}

private fun Interceptor.Chain.test() = buildString {
    HttpLoggingInterceptor { appendln(it) }
        .intercept(this@test)
}

private fun setupMockChain(init: HttpGetContext.() -> Unit): Interceptor.Chain =
    mockChain().apply {
        every { request() } returns HttpGetContext().apply(init).makeRequest()
    }

private fun setupPostMockChain(init: HttpPostContext.() -> Unit): Interceptor.Chain =
    mockChain().apply {
        every { request() } returns HttpPostContext().apply(init).makeRequest()
    }


private fun mockChain() = mockk<Interceptor.Chain>().apply {
    val response = mockk<Response>()
    every { response.code() } returns 200
    every { response.headers() } returns Headers.Builder().add("a", "b").build()
    every { response.request().url() } returns HttpUrl.get("http://127.0.0.1")
    every { proceed(any()) } returns response
    every { connection() } returns mockk<Connection>().apply {
        every { protocol() } returns Protocol.HTTP_1_1
    }
}

const val UUID_REGEX = "[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}"
