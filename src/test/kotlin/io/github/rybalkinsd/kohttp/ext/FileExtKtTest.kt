package io.github.rybalkinsd.kohttp.ext

import io.github.rybalkinsd.kohttp.util.asJson
import org.junit.Test
import java.io.File
import java.net.URL
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class FileExtKtTest {

    @Test
    fun `upload file using File and string destination`() {
        val file = File(this.javaClass.getResource("/cat.gif").toURI())
        val response = file.upload("http://postman-echo.com/post")

        assertEquals(200, response.code())
        val parsedResponse = response.body()?.string().asJson()
        assertEquals(1046214, parsedResponse["headers"]["content-length"].asInt())
        assertTrue { parsedResponse["headers"]["content-type"].asText().startsWith("multipart/mixed; boundary=") }
    }

    @Test
    fun `upload file using File and URL destination`() {
        val file = File(this.javaClass.getResource("/cat.gif").toURI())
        val response = file.upload(URL("http://postman-echo.com/post"))

        assertEquals(200, response.code())
        val parsedResponse = response.body()?.string().asJson()
        assertEquals(1046214, parsedResponse["headers"]["content-length"].asInt())
        assertTrue { parsedResponse["headers"]["content-type"].asText().startsWith("multipart/mixed; boundary=") }
    }

}