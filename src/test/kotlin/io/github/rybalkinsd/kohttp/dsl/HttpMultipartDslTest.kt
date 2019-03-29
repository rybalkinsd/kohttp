package io.github.rybalkinsd.kohttp.dsl

import io.github.rybalkinsd.kohttp.client.defaultHttpClient
import io.github.rybalkinsd.kohttp.client.fork
import io.github.rybalkinsd.kohttp.interceptors.LoggingInterceptor
import org.junit.Test

class HttpMultipartDslTest {

    @Test
    fun `simple multipart request`() {
        val client = defaultHttpClient.fork {
            interceptors {
                +LoggingInterceptor(::println)
            }
        }
        val r = httpPost(client) {
            host = "postman-echo.com"
            path = "/post"

            multipartBody {
                +string("123")
                +form("a=c&d=e")
            }
//            body {
//                multipart {
//                    builder.addFormDataPart("file", "filename", file(File(this.javaClass.getResource("/cat.gif").toURI())))
//                    +form { "1" to 2 }
//                }
//
//            }
//                    +file(File(this.javaClass.getResource("/cat.gif").toURI()))
        }

        println(r)
    }

}