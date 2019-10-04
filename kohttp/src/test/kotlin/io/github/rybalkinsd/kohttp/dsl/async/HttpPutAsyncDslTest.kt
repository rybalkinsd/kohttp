package io.github.rybalkinsd.kohttp.dsl.async

import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * @author sergey, gokul
 */
class HttpPutAsyncDslTest {

    @Test
    fun `put request with form # postman echo`() {
        val response = httpPutAsync {
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
}