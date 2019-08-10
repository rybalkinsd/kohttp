package io.github.rybalkinsd.kohttp.dsl.async

import io.github.rybalkinsd.kohttp.dsl.async.uploadAsync
import io.github.rybalkinsd.kohttp.jackson.ext.asJson
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UploadAsyncDslTest {

    @Test
    fun `small file upload`() {
        val response = uploadAsync {
            url("http://postman-echo.com/post")
            val fileUri = this.javaClass.getResource("/cat.gif").toURI()
            file(fileUri)
        }
        Assert.assertFalse("After coroutine call, we must have not-ready response", response.isCompleted)

        runBlocking {
            response.await().use {
                val parsedResponse = it.asJson()

                assertEquals(200, it.code())
                assertEquals(1046214, parsedResponse["headers"]["content-length"].asInt())
                assertTrue { parsedResponse["headers"]["content-type"].asText().startsWith("multipart/mixed; boundary=") }
            }
        }
    }
}
