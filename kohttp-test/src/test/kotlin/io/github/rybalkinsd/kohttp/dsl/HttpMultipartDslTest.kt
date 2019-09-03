package io.github.rybalkinsd.kohttp.dsl

import io.github.rybalkinsd.kohttp.jackson.ext.toJson
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
                +part("cat") {
                    file(File(this.javaClass.getResource("/cat.gif").toURI()))
                }
            }
        }

        val parsedResponse = response.toJson()
        assertEquals(1046193, parsedResponse["headers"]["content-length"].asInt())
        assertTrue { parsedResponse["headers"]["content-type"].asText().startsWith("multipart/mixed; boundary=") }
    }

    @Test
    fun `multipart request with file and custom type`() {
        val response = httpPost {
            host = "postman-echo.com"
            path = "/post"

            multipartBody("multipart/alternative") {
                +part("cat", "cat.gif") {
                    file(File(this.javaClass.getResource("/cat.gif").toURI()))
                }
            }
        }

        val parsedResponse = response.toJson()
        assertEquals(1046213, parsedResponse["headers"]["content-length"].asInt())
        assertTrue { parsedResponse["headers"]["content-type"].asText().startsWith("multipart/alternative; boundary=") }
    }

}
