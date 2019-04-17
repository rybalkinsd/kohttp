package io.github.rybalkinsd.kohttp.dsl

import io.github.rybalkinsd.kohttp.client.client
import io.github.rybalkinsd.kohttp.interceptors.LoggingInterceptor
import io.github.rybalkinsd.kohttp.util.asJson
import org.junit.Test
import kotlin.test.assertEquals

class UploadDslKtTest {

    @Test
    fun `small file upload`() {
        val client = client {
            interceptors {
                +LoggingInterceptor(::println)
            }
        }

        val r  = upload(client) {
            url("http://postman-echo.com/post")
            val fileUri = this.javaClass.getResource("/cat.gif").toURI()
            file(fileUri)
        }

        println(r)
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
        assertEquals(5243053, uploadResponse.body()?.string().asJson()["headers"]["content-length"].asInt())
    }
}
