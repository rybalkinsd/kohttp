package io.github.rybalkinsd.kohttp.dsl

import io.github.rybalkinsd.kohttp.assertContainsAtLeast
import io.github.rybalkinsd.kohttp.ext.asJson
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

        val parsedResponse = r.asJson()
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
        assertEquals(1 * 1024 * 1024 + 173, uploadResponse.asJson()["headers"]["content-length"].asInt())
    }

    @Test
    fun `test file upload with headers and parameters`() {

        val expectedHeaders = mapOf(
                "one" to "42",
                "cookie" to "aaa=bbb; ccc=42"
        )

        val expectedArgs = mapOf(
                "listOfParams" to """["val1","val2","val3"]""",
                "query" to "cat"
        )


        val r = upload {

            header {
                "one" to 42
                cookie {
                    "aaa" to "bbb"
                    "ccc" to 42
                }
            }

            param {
                "listOfParams" to listOf("val1", "val2", "val3")
                "query" to "cat"
            }

            url("http://postman-echo.com/post")
            val fileUri = this.javaClass.getResource("/cat.gif").toURI()
            file(fileUri)
        }

        val parsedResponse = r.asJson()

        assertEquals(expectedArgs["query"], parsedResponse["args"]["query"].asText())
        assertEquals(expectedArgs["listOfParams"], parsedResponse["args"]["listOfParams"].toString())
        assertContainsAtLeast(expectedHeaders, parsedResponse["headers"])
        assertEquals(1046214, parsedResponse["headers"]["content-length"].asInt())
        assertTrue { parsedResponse["headers"]["content-type"].asText().startsWith("multipart/mixed; boundary=") }
    }
}
