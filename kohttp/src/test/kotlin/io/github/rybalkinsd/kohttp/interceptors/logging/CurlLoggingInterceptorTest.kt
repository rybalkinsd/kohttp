package io.github.rybalkinsd.kohttp.interceptors.logging

import io.github.rybalkinsd.kohttp.dsl.context.HttpGetContext
import io.github.rybalkinsd.kohttp.dsl.context.HttpPostContext
import io.github.rybalkinsd.kohttp.ext.url
import io.mockk.every
import io.mockk.mockk
import okhttp3.Interceptor
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * @author doyaaaaaken, sergey
 */
class CurlLoggingInterceptorTest {

    @Test
    fun `curl of simple get request`() {
        val chain = setupMockChain {
            url("https://postman-echo.com/get")
        }

        val result = chain.test()

        assertThat(result).isEqualTo(
            """
               -->
               curl -X GET "https://postman-echo.com/get"
               ---
               
            """.trimIndent())
    }

    @Test
    fun `curl of request with header`() {
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

        assertThat(result).isEqualTo(
            """
               -->
               curl -X GET -H "cookie: foo=6; bar=28" -H "Content-Type: application/json" "https://postman-echo.com/get"
               ---
               
            """.trimIndent())
    }

    @Test
    fun `curl of request with body`() {
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

        assertThat(result).isEqualTo(
            """
               -->
               curl -X POST -d 'foo=123&bar=abc' "https://postman-echo.com/post"
               ---
               
            """.trimIndent())
    }
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
    every { proceed(any()) } returns mockk()
}

private fun Interceptor.Chain.test() = buildString {
    CurlLoggingInterceptor { appendLine(it) }
        .intercept(this@test)
}
