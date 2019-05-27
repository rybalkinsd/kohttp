package io.github.rybalkinsd.kohttp.ext

import io.github.rybalkinsd.kohttp.util.asJson
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.io.File
import java.net.URL
import kotlin.test.assertEquals
import kotlin.test.assertTrue

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
                val parsedResponse = it.body()?.string().asJson()

                assertEquals(200, it.code())
                assertEquals(1046214, parsedResponse["headers"]["content-length"].asInt())
                assertTrue { parsedResponse["headers"]["content-type"].asText().startsWith("multipart/mixed; boundary=") }
            }
        }
    }

    @Test
    fun `async upload file using File and URL destination`() {
        val file = File(this.javaClass.getResource("/cat.gif").toURI())
        val response = file.uploadAsync(URL("http://postman-echo.com/post"))

        runBlocking {
            response.await().use {
                val parsedResponse = it.body()?.string().asJson()

                assertEquals(200, it.code())
                assertEquals(1046214, parsedResponse["headers"]["content-length"].asInt())
                assertTrue { parsedResponse["headers"]["content-type"].asText().startsWith("multipart/mixed; boundary=") }
            }
        }
    }
}