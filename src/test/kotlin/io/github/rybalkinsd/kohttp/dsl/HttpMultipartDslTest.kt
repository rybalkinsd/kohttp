package io.github.rybalkinsd.kohttp.dsl

import io.github.rybalkinsd.kohttp.ext.asJson
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class HttpMultipartDslTest {

    @Test
    fun `multipart request with file`() {
        val response = httpPost {
            host = "postman-echo.com"
            path = "/post"

            multipartBody {
                +form("cat", File(this.javaClass.getResource("/cat.gif").toURI()))
            }
        }

        val parsedResponse = response.asJson()
        assertEquals(1046213, parsedResponse["headers"]["content-length"].asInt())
        assertTrue { parsedResponse["headers"]["content-type"].asText().startsWith("multipart/mixed; boundary=") }
    }

    @Test
    fun `multipart request with file and custom type`() {
        val response = httpPost {
            host = "postman-echo.com"
            path = "/post"

            multipartBody("multipart/alternative") {
                +form("cat", File(this.javaClass.getResource("/cat.gif").toURI()))
            }
        }

        val parsedResponse = response.asJson()
        assertEquals(1046213, parsedResponse["headers"]["content-length"].asInt())
        assertTrue { parsedResponse["headers"]["content-type"].asText().startsWith("multipart/alternative; boundary=") }
    }

}
