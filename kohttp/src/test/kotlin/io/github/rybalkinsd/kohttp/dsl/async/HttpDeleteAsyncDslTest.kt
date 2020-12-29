package io.github.rybalkinsd.kohttp.dsl.async

import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * @author sergey, gokul, evgeny
 */
class HttpDeleteAsyncDslTest {

    @Test
    fun `delete request with form # postman echo`() {
        val response = httpDeleteAsync {
            host = "postman-echo.com"
            path = "/delete"
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