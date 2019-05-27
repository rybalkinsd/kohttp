package io.github.rybalkinsd.kohttp.dsl.async

import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import kotlin.test.assertEquals

/**
 * @author sergey, alex, gokul
 */
class HttpPostAsyncDslTest {

    @Test
    fun `post request with form # postman echo`() {
        val response = httpPostAsync {
            host = "postman-echo.com"
            path = "/post"
        }
        Assert.assertFalse("After coroutine call, we must have not-ready response", response.isCompleted)

        runBlocking {
            response.await().use {
                assertEquals(200, it.code())
            }
        }
    }
}
