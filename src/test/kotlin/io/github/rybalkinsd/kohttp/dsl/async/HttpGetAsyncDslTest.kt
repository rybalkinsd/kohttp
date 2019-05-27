package io.github.rybalkinsd.kohttp.dsl.async

import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import kotlin.test.assertEquals

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
        Assert.assertFalse("After coroutine call, we must have not-ready response", response.isCompleted)

        runBlocking {
            response.await().use {
                assertEquals(200, it.code())
            }
        }
    }
}
