package io.github.rybalkinsd.kohttp.dsl.async

import io.github.rybalkinsd.kohttp.jackson.ext.toJson
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class UploadAsyncDslTest {

    @Test
    fun `small file upload`() {
        val response = uploadAsync {
            url("http://postman-echo.com/post")
            val fileUri = this.javaClass.getResource("/cat.gif").toURI()
            file(fileUri)
        }
        assertThat(response.isCompleted)
                .`as`("After coroutine call, we must have not-ready response")
                .isFalse()

        runBlocking {
            response.await().use {
                val parsedResponse = it.toJson()

                assertThat(it.code()).isEqualTo(200)
                assertThat(parsedResponse["headers"]["content-length"].asInt()).isEqualTo(1046214)
                assertThat(parsedResponse["headers"]["content-type"].asText()).startsWith("multipart/mixed; boundary=")
            }
        }
    }
}
