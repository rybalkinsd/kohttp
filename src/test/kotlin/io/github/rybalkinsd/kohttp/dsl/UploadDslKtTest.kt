package io.github.rybalkinsd.kohttp.dsl

import io.github.rybalkinsd.kohttp.ext.asJson
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UploadDslKtTest {

    @Test
    fun `small file upload`() {
        val r  = upload {
            url("http://postman-echo.com/post")
            val fileUri = this.javaClass.getResource("/cat.gif").toURI()
            file(fileUri)
        }

        val parsedResponse = r.asJson()
        assertEquals(1046214, parsedResponse["headers"]["content-length"].asInt())
        assertTrue { parsedResponse["headers"]["content-type"].asText().startsWith("multipart/mixed; boundary=") }
    }

    @Test
    fun `download file then upload`() {
        val downloadResponse = httpGet {
            host = "ipv4.download.thinkbroadband.com"
            path = "/5MB.zip"
        }

        assertEquals(200 ,downloadResponse.code())
        assertEquals(5242880, downloadResponse.body()?.contentLength())

        val uploadResponse = upload {
            url("http://postman-echo.com/post")
            val data = downloadResponse.body()?.bytes()!!
            bytes("data", data)
        }

        assertEquals(200, uploadResponse.code())
        assertEquals(5243053, uploadResponse.asJson()["headers"]["content-length"].asInt())
    }
}
