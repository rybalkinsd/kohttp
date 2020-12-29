package io.github.rybalkinsd.kohttp.dsl

import io.github.rybalkinsd.kohttp.jackson.ext.toJson
import io.github.rybalkinsd.kohttp.assertContainsAtLeast
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class UploadDslKtTest {

    @Test
    fun `small file upload`() {
        val r = upload {
            url("http://postman-echo.com/post")
            val fileUri = this.javaClass.getResource("/cat.gif").toURI()
            file(fileUri)
        }

        val parsedResponse = r.toJson()
        assertThat(parsedResponse["headers"]["content-length"].asInt()).isEqualTo(1046214)
        assertThat(parsedResponse["headers"]["content-type"].asText()).startsWith("multipart/mixed; boundary=")
    }

    @Test
    fun `download file then upload`() {
        val downloadResponse = httpGet {
            host = "speedtest.ftp.otenet.gr"
            path = "/files/test1Mb.db"
        }

        assertThat(downloadResponse.code).isEqualTo(200)
        assertThat(downloadResponse.body?.contentLength()).isEqualTo(1 * 1024 * 1024)

        val uploadResponse = upload {
            url("http://postman-echo.com/post")
            val data = downloadResponse.body?.bytes()!!
            bytes("data", data)
        }

        assertThat(uploadResponse.code).isEqualTo(200)
        assertThat(uploadResponse.toJson()["headers"]["content-length"].asInt()).isEqualTo(1 * 1024 * 1024 + 173)
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

        val parsedResponse = r.toJson()

        assertThat(parsedResponse["args"]["query"].asText()).isEqualTo(expectedArgs["query"])
        assertThat(parsedResponse["args"]["listOfParams"].toString()).isEqualTo(expectedArgs["listOfParams"])
        assertContainsAtLeast(expectedHeaders, parsedResponse["headers"])
        assertThat(parsedResponse["headers"]["content-length"].asInt()).isEqualTo(1046214)
        assertThat(parsedResponse["headers"]["content-type"].asText()).startsWith("multipart/mixed; boundary=")
    }
}
