package io.github.rybalkinsd.kohttp.dsl.async

import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * @author sergey
 */
class HttpHeadAsyncDslTest {

    @Test
    fun `single sync http head invocation with param`() {
        val response = httpHeadAsync {
            host = "yandex.ru"
            path = "/search"
            port = 80
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