package io.github.rybalkinsd.kohttp.dsl

import io.github.rybalkinsd.kohttp.jackson.ext.toJson
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.io.File

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
        assertThat(parsedResponse["headers"]["content-length"].asInt()).isEqualTo(1046193)
        assertThat(parsedResponse["headers"]["content-type"].asText()).startsWith("multipart/mixed; boundary=")
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
        assertThat(parsedResponse["headers"]["content-length"].asInt()).isEqualTo(1046213)
        assertThat(parsedResponse["headers"]["content-type"].asText()).startsWith("multipart/alternative; boundary=")
    }

}
