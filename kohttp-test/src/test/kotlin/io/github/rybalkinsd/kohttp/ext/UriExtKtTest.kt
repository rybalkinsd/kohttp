package io.github.rybalkinsd.kohttp.ext

import io.github.rybalkinsd.kohttp.jackson.ext.toJson
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.net.URL


class UriExtKtTest {

    @Test
    fun `upload file using URI and string destination`() {
        val fileUri = this.javaClass.getResource("/cat.gif").toURI()
        val response = fileUri.upload("http://postman-echo.com/post")

        assertThat(response.code).isEqualTo(200)
        val parsedResponse = response.toJson()
        assertThat(parsedResponse["headers"]["content-length"].asInt()).isEqualTo(1046214)
        assertThat(parsedResponse["headers"]["content-type"].asText()).startsWith("multipart/mixed; boundary=")
    }

    @Test
    fun `upload file using URI and URL destination`() {
        val fileUri = this.javaClass.getResource("/cat.gif").toURI()
        val response = fileUri.upload(URL("http://postman-echo.com/post"))

        assertThat(response.code).isEqualTo(200)
        val parsedResponse = response.toJson()
        assertThat(parsedResponse["headers"]["content-length"].asInt()).isEqualTo(1046214)
        assertThat(parsedResponse["headers"]["content-type"].asText()).startsWith("multipart/mixed; boundary=")
    }
}
