package io.github.rybalkinsd.kohttp.ext

import io.github.rybalkinsd.kohttp.ext.upload
import io.github.rybalkinsd.kohttp.jackson.ext.asJson
import org.junit.Test
import java.net.URL
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UriExtKtTest {

    @Test
    fun `upload file using URI and string destination`() {
        val fileUri = this.javaClass.getResource("/cat.gif").toURI()
        val response = fileUri.upload("http://postman-echo.com/post")

        assertEquals(200, response.code())
        val parsedResponse = response.asJson()
        assertEquals(1046214, parsedResponse["headers"]["content-length"].asInt())
        assertTrue { parsedResponse["headers"]["content-type"].asText().startsWith("multipart/mixed; boundary=") }
    }

    @Test
    fun `upload file using URI and URL destination`() {
        val fileUri = this.javaClass.getResource("/cat.gif").toURI()
        val response = fileUri.upload(URL("http://postman-echo.com/post"))

        assertEquals(200, response.code())
        val parsedResponse = response.asJson()
        assertEquals(1046214, parsedResponse["headers"]["content-length"].asInt())
        assertTrue { parsedResponse["headers"]["content-type"].asText().startsWith("multipart/mixed; boundary=") }
    }
}
