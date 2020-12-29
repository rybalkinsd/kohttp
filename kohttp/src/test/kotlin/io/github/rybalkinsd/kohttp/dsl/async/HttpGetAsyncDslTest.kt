package io.github.rybalkinsd.kohttp.dsl.async

import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * @author sergey
 */
class HttpGetAsyncDslTest {

    @Test
    fun `async http get request`() {
        val response = httpGetAsync {
            host = "postman-echo.com"
            path = "/delay/1"
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
