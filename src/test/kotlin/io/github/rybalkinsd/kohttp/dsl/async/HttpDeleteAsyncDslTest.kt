package io.github.rybalkinsd.kohttp.dsl.async

import io.github.rybalkinsd.kohttp.assertResponses
import io.github.rybalkinsd.kohttp.util.asJson
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import kotlin.test.assertEquals

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
        Assert.assertFalse("After coroutine call, we must have not-ready response", response.isCompleted)

        runBlocking {
            response.await().use {
                assertEquals(200, it.code())
            }
        }
    }
}