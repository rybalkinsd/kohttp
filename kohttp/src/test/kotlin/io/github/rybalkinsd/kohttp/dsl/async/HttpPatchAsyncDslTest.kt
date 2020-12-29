package io.github.rybalkinsd.kohttp.dsl.async

import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * @author bpaxio, gokul, evgeny
 */
class HttpPatchAsyncDslTest {

    @Test
    fun `patch request with form # postman echo`() {
        val response = httpPatchAsync {
            host = "postman-echo.com"
            path = "/patch"
        }
        assertThat(response.isCompleted)
                .`as`("After coroutine call, we must have not-ready response")
                .isFalse()

        runBlocking {
            response.await().use {
                assertThat(it.code).isEqualTo(200)
            }
        }
    }
}