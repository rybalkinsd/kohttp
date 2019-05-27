package io.github.rybalkinsd.kohttp.dsl.async

import io.github.rybalkinsd.kohttp.assertResponses
import io.github.rybalkinsd.kohttp.dsl.httpPost
import io.github.rybalkinsd.kohttp.util.asJson
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertTrue

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
