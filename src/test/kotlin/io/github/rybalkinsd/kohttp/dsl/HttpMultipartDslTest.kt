package io.github.rybalkinsd.kohttp.dsl

import io.github.rybalkinsd.kohttp.client.defaultHttpClient
import io.github.rybalkinsd.kohttp.client.fork
import io.github.rybalkinsd.kohttp.interceptors.LoggingInterceptor
import io.github.rybalkinsd.kohttp.util.asJson
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class HttpMultipartDslTest {

    @Test
    fun `simple multipart request`() {
        val client = defaultHttpClient.fork {
            interceptors {
                +LoggingInterceptor(::println)
            }
        }

        val response = httpPost(client) {
            host = "postman-echo.com"
            path = "/post"

            multipartBody {
                +form("cat", File(this.javaClass.getResource("/cat.gif").toURI()))
            }
        }

        val parsedResponse = response.body()?.string().asJson()
        assertEquals(1046213, parsedResponse["headers"]["content-length"].asInt())
        assertTrue { parsedResponse["headers"]["content-type"].asText().startsWith("multipart/mixed; boundary=") }
    }

}