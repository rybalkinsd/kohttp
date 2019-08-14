package io.github.rybalkinsd.kohttp.dsl.async

import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import kotlin.test.assertEquals

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
        Assert.assertFalse("After coroutine call, we must have not-ready response", response.isCompleted)

        runBlocking {
            response.await().use {
                assertEquals(200, it.code())
            }
        }
    }
}