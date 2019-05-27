package io.github.rybalkinsd.kohttp.dsl

import io.github.rybalkinsd.kohttp.util.asJson
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UploadDslKtTest {

    @Test
    fun `small file upload`() {
        val r = upload {
            url("http://postman-echo.com/post")
            val fileUri = this.javaClass.getResource("/cat.gif").toURI()
            file(fileUri)
        }

        val parsedResponse = r.body()?.string().asJson()
        assertEquals(1046214, parsedResponse["headers"]["content-length"].asInt())
        assertTrue { parsedResponse["headers"]["content-type"].asText().startsWith("multipart/mixed; boundary=") }
    }

    @Test
    fun `download file then upload`() {
        val downloadResponse = httpGet {
            host = "speedtest.ftp.otenet.gr"
            path = "/files/test1Mb.db"
        }

        assertEquals(200, downloadResponse.code())
        assertEquals(1 * 1024 * 1024, downloadResponse.body()?.contentLength())

        val uploadResponse = upload {
            url("http://postman-echo.com/post")
            val data = downloadResponse.body()?.bytes()!!
            bytes("data", data)
        }

        assertEquals(200, uploadResponse.code())
        assertEquals(1 * 1024 * 1024 + 173, uploadResponse.body()?.string().asJson()["headers"]["content-length"].asInt())
    }
}
