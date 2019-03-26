package io.github.rybalkinsd.kohttp.dsl

import io.github.rybalkinsd.kohttp.ext.eager
import org.junit.Test
import java.io.File

class HttpMultipartDslTest {

    @Test
    fun `simple multipart request`() {
        val r = httpPost {
            host = "postman-echo.com"
            path = "/post"

            body {
                multipart {
                    +file(File( this.javaClass.getResource("/cat.gif").toURI()))
                }
            }
        }

        val re = r.eager()
    }
}