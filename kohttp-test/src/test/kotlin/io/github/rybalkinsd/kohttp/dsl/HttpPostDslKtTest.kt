package io.github.rybalkinsd.kohttp.dsl

import io.github.rybalkinsd.kohttp.assertContainsAtLeast
import io.github.rybalkinsd.kohttp.assertContainsExactly
import io.github.rybalkinsd.kohttp.client.defaultHttpClient
import io.github.rybalkinsd.kohttp.client.fork
import io.github.rybalkinsd.kohttp.interceptors.logging.HttpLoggingInterceptor
import io.github.rybalkinsd.kohttp.jackson.ext.toJson
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.io.File

/**
 * @author sergey, alex, gokul
 */
class HttpPostDslKtTest {

    @Test
    fun `post request with form # postman echo`() {
        val expectedHeader = mapOf(
            "one" to "42",
            "cookie" to "aaa=bbb; ccc=42"
        )

        val expectedParams = mapOf(
            "arg" to "iphone"
        )

        val expectedForm = mapOf(
            "login" to "user",
            "email" to "john.doe@gmail.com"
        )

        val client = defaultHttpClient.fork {
            interceptors {
                +HttpLoggingInterceptor()
            }
        }

        httpPost(client) {
            host = "postman-echo.com"
            path = "/post"

            param {
                "arg" to "iphone"
            }

            header {
                "one" to 42
                cookie {
                    "aaa" to "bbb"
                    "ccc" to 42
                }
            }

            body {
                form {
                    "login" to "user"
                    "email" to "john.doe@gmail.com"
                }
            }
        }.use {
            val parsedResponse = it.toJson()
            assertContainsAtLeast(expectedHeader, parsedResponse["headers"])
            assertContainsExactly(expectedParams, parsedResponse["args"])
            assertContainsExactly(expectedForm, parsedResponse["form"])
            assertThat(it.code).isEqualTo(200)
        }
    }

    @Test
    fun `post request with form encoded # postman echo`() {
        val expectedForm = mapOf(
            "encoded" to " ",
            "notEncoded" to "%20"
        )

        httpPost {
            host = "postman-echo.com"
            path = "/post"

            body {
                form {
                    addEncoded("encoded", "%20")
                    "notEncoded" to "%20"
                }
            }
        }.use {
            val parsedResponse = it.toJson()
            assertContainsExactly(expectedForm, parsedResponse["form"])
            assertThat(it.code).isEqualTo(200)
        }
    }

    @Test
    fun `post request with form string # postman echo`() {
        httpPost {
            host = "postman-echo.com"
            path = "/post"

            body {
                form("login=user&email=john.doe@gmail.com")
            }
        }.use {
            println(it.body?.string())
        }
    }

    @Test
    fun `post request with json # postman echo`() {
        val expectedHeader = mapOf(
            "one" to "42",
            "cookie" to "aaa=bbb; ccc=42"
        )

        val expectedParams = mapOf(
            "arg" to "iphone"
        )

        val expectedJson = mapOf(
            "login" to "user",
            "email" to "john.doe@gmail.com"
        )
        val response = httpPost {
            host = "postman-echo.com"
            path = "/post"

            param {
                "arg" to "iphone"
            }

            header {
                "one" to 42
                cookie {
                    "aaa" to "bbb"
                    "ccc" to 42
                }
            }

            body {
                json {
                    "login" to "user"
                    "email" to "john.doe@gmail.com"
                }
            }
        }

        response.use {
            val parsedResponse = it.toJson()
            assertContainsAtLeast(expectedHeader, parsedResponse["headers"])
            assertContainsExactly(expectedParams, parsedResponse["args"])
            assertContainsExactly(expectedJson, parsedResponse["json"])
            assertThat(it.code).isEqualTo(200)
        }
    }

    @Test
    fun `post request with json string # postman echo`() {
        val response = httpPost {
            host = "postman-echo.com"
            path = "/post"

            body("application/json") {
                string("""{"login":"user","email":"john.doe@gmail.com"}""")
            }
        }

        val expectedJson = mapOf(
            "login" to "user",
            "email" to "john.doe@gmail.com"
        )

        response.use {
            val parsedResponse = it.toJson()
            assertContainsExactly(expectedJson, parsedResponse["json"])
            assertThat(it.code).isEqualTo(200)
        }
    }

    @Test
    fun `post request with json string 2 # postman echo`() {
        val response = httpPost {
            host = "postman-echo.com"
            path = "/post"

            body {
                json("""{"login":"user","email":"john.doe@gmail.com"}""")
            }
        }

        val expectedJson = mapOf(
            "login" to "user",
            "email" to "john.doe@gmail.com"
        )

        response.use {
            val parsedResponse = it.toJson()
            assertContainsExactly(expectedJson, parsedResponse["json"])
            assertThat(it.code).isEqualTo(200)
        }
    }

    @Test
    fun `post request with file # postman echo`() {
        val response = httpPost {
            host = "postman-echo.com"
            path = "/post"

            body("image/gif") {
                val fileUrl = this.javaClass.getResource("/cat.gif")
                file(File(fileUrl.toURI()))
            }
        }

        response.use {
            with(it) {
                assertThat(toJson()["headers"]["content-length"].asLong()).isGreaterThan(100_000)
                assertThat(it.code).isEqualTo(200)
            }
        }
    }

    @Test
    fun `post request with byte array # postman echo`() {
        val response = httpPost {
            host = "postman-echo.com"
            path = "/post"

            body {
                bytes("string of bytes".toByteArray())
            }
        }

        response.use {
            val parsedResponse = it.toJson()
            assertThat(parsedResponse["headers"]["content-length"].asInt()).isEqualTo(15)
            assertThat(it.code).isEqualTo(200)
        }
    }

    @Test
    fun `post request with empty body# postman echo`() {
        val expectedHeader = mapOf(
            "one" to "42",
            "cookie" to "aaa=bbb; ccc=42"
        )

        val expectedParams = mapOf(
            "arg" to "iphone"
        )

        httpPost {
            host = "postman-echo.com"
            path = "/post"

            param {
                "arg" to "iphone"
            }

            header {
                "one" to 42
                cookie {
                    "aaa" to "bbb"
                    "ccc" to 42
                }
            }
        }.use {
            val parsedResponse = it.toJson()
            val headers = parsedResponse["headers"]
            assertContainsAtLeast(expectedHeader, headers)
            assertContainsExactly(expectedParams, parsedResponse["args"])
            assertThat(headers["content-length"].asInt()).isEqualTo(0)
            assertThat(it.code).isEqualTo(200)
        }
    }
}
