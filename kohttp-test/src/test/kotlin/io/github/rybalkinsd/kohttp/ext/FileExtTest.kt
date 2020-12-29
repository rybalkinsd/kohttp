package io.github.rybalkinsd.kohttp.ext

import io.github.rybalkinsd.kohttp.jackson.ext.toJson
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.io.File
import java.net.URL

class FileExtTest {

    @Test
    fun `upload file using File and string destination`() {
        val file = File(this.javaClass.getResource("/cat.gif").toURI())
        val response = file.upload("http://postman-echo.com/post")

        assertThat(response.code).isEqualTo(200)
        val parsedResponse = response.toJson()
        assertThat(parsedResponse["headers"]["content-length"].asInt()).isEqualTo(1046214)
        assertThat(parsedResponse["headers"]["content-type"].asText()).startsWith("multipart/mixed; boundary=")
    }

    @Test
    fun `upload file using File and URL destination`() {
        val file = File(this.javaClass.getResource("/cat.gif").toURI())
        val response = file.upload(URL("http://postman-echo.com/post"))

        assertThat(response.code).isEqualTo(200)
        val parsedResponse = response.toJson()
        assertThat(parsedResponse["headers"]["content-length"].asInt()).isEqualTo(1046214)
        assertThat(parsedResponse["headers"]["content-type"].asText()).startsWith("multipart/mixed; boundary=")
    }

}