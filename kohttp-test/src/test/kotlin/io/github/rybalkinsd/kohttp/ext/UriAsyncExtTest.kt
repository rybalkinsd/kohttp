package io.github.rybalkinsd.kohttp.ext

import io.github.rybalkinsd.kohttp.ext.uploadAsync
import io.github.rybalkinsd.kohttp.jackson.ext.asJson
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.net.URL
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * @author evgeny
 */
class UriAsyncExtTest {

    @Test
    fun `async upload file using URI and string destination`() {
        val fileUri = this.javaClass.getResource("/cat.gif").toURI()
        val response = fileUri.uploadAsync("http://postman-echo.com/post")

        runBlocking {
            response.await().use {
                val parsedResponse = it.asJson()

                assertEquals(200, it.code())
                assertEquals(1046214, parsedResponse["headers"]["content-length"].asInt())
                assertTrue { parsedResponse["headers"]["content-type"].asText().startsWith("multipart/mixed; boundary=") }
            }
        }
    }

    @Test
    fun `async upload file using URI and URL destination`() {
        val fileUri = this.javaClass.getResource("/cat.gif").toURI()
        val response = fileUri.uploadAsync(URL("http://postman-echo.com/post"))

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
