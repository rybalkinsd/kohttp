package io.github.rybalkinsd.kohttp.ext

import io.github.rybalkinsd.kohttp.jackson.ext.toJson
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.io.File
import java.net.URL

/**
 * @author evgeny
 */
class FileAsyncExtTest {

    @Test
    fun `async upload file using File and string destination`() {
        val file = File(this.javaClass.getResource("/cat.gif").toURI())
        val response = file.uploadAsync("http://postman-echo.com/post")

        runBlocking {
            response.await().use {
                val parsedResponse = it.toJson()

                assertThat(it.code).isEqualTo(200)
                assertThat(parsedResponse["headers"]["content-length"].asInt()).isEqualTo(1046214)
                assertThat(parsedResponse["headers"]["content-type"].asText()).startsWith("multipart/mixed; boundary=")
            }
        }
    }

    @Test
    fun `async upload file using File and URL destination`() {
        val file = File(this.javaClass.getResource("/cat.gif").toURI())
        val response = file.uploadAsync(URL("http://postman-echo.com/post"))

        runBlocking {
            response.await().use {
                val parsedResponse = it.toJson()

                assertThat(it.code).isEqualTo(200)
                assertThat(parsedResponse["headers"]["content-length"].asInt()).isEqualTo(1046214)
                assertThat(parsedResponse["headers"]["content-type"].asText()).startsWith("multipart/mixed; boundary=")
            }
        }
    }
}