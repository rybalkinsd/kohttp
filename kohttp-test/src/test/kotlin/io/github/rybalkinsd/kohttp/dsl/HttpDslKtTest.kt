package io.github.rybalkinsd.kohttp.dsl

import io.github.rybalkinsd.kohttp.assertContainsAtLeast
import io.github.rybalkinsd.kohttp.assertContainsExactly
import io.github.rybalkinsd.kohttp.client.defaultHttpClient
import io.github.rybalkinsd.kohttp.client.fork
import io.github.rybalkinsd.kohttp.dsl.context.*
import io.github.rybalkinsd.kohttp.interceptors.logging.HttpLoggingInterceptor
import io.github.rybalkinsd.kohttp.jackson.ext.toJson
import org.assertj.core.api.Assertions.*
import org.junit.Test
import java.io.File

/**
 * @author sergey, alex, gokul
 */
class HttpDslKtTest {

    @Test
    fun `post request with explicitly using HttpPostContext and discarding the HttpMethod`() {
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

        http(client, init = fun HttpPostContext.() {
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
        }).use {
            val parsedResponse = it.toJson()
            assertContainsAtLeast(expectedHeader, parsedResponse["headers"])
            assertContainsExactly(expectedParams, parsedResponse["args"])
            assertContainsExactly(expectedForm, parsedResponse["form"])
            assertThat(it.code()).isEqualTo(200)
        }
    }

    @Test
    fun `post request with explicitly using HttpContext and http post method`() {
        val expectedHeader = mapOf(
                "one" to "42",
                "cookie" to "aaa=bbb; ccc=42"
        )

        val expectedParams = mapOf(
                "arg" to "iphone"
        )

        val client = defaultHttpClient.fork {
            interceptors {
                +HttpLoggingInterceptor()
            }
        }

        http(client, method = Method.POST, init = fun HttpContext.() {
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
        }).use {
            val parsedResponse = it.toJson()
            assertContainsAtLeast(expectedHeader, parsedResponse["headers"])
            assertContainsExactly(expectedParams, parsedResponse["args"])
            assertThat(it.code()).isEqualTo(200)
        }
    }

    @Test
    fun `post request with explicitly using HttpGetContext and http post method should throw exception`() {
        val client = defaultHttpClient.fork {
            interceptors {
                +HttpLoggingInterceptor()
            }
        }

        assertThatThrownBy {
            http(client, method = Method.POST, init = fun HttpGetContext.() {
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
            })
        }.isInstanceOf(ClassCastException::class.java)
    }

    @Test
    fun `get request with explicitly using HttpGetContext and discarding the HttpMethod`() {
        val response = http(init = fun HttpGetContext.() {
            host = "yandex.ru"
            path = "/search"
            port = 80

            param {
                "text" to "iphone"
                "lr" to 213
            }
        })

        response.use {
            assertThat(it.code()).isEqualTo(200)
        }
    }

    @Test
    fun `get request with explicitly using HttpContext and http get method`() {
        val response = http(method = Method.GET, init = fun HttpContext.() {
            host = "yandex.ru"
            path = "/search"
            port = 80

            param {
                "text" to "iphone"
                "lr" to 213
            }
        })

        response.use {
            assertThat(it.code()).isEqualTo(200)
        }
    }

    @Test
    fun `get request with explicitly using HttpPostContext and http get method should throw exception`() {
        assertThatThrownBy {
            http(method = Method.GET, init = fun HttpPostContext.() {
                host = "yandex.ru"
                path = "/search"
                port = 80

                param {
                    "text" to "iphone"
                    "lr" to 213
                }
            })
        }.isInstanceOf(ClassCastException::class.java)
    }

    @Test
    fun `delete request with explicitly using HttpDeleteContext and discarding the HttpMethod`() {
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

        http(init = fun HttpDeleteContext.() {
            host = "postman-echo.com"
            path = "/delete"

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
        }).use {
            val parsedResponse = it.toJson()
            assertContainsAtLeast(expectedHeader, parsedResponse["headers"])
            assertContainsExactly(expectedParams, parsedResponse["args"])
            assertContainsExactly(expectedForm, parsedResponse["form"])
            assertThat(it.code()).isEqualTo(200)
        }
    }

    @Test
    fun `delete request with explicitly using HttpContext and http delete method`() {
        val expectedHeader = mapOf(
                "one" to "42",
                "cookie" to "aaa=bbb; ccc=42"
        )

        val expectedParams = mapOf(
                "arg" to "iphone"
        )

        http(method = Method.DELETE, init = fun HttpContext.() {
            host = "postman-echo.com"
            path = "/delete"

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
        }).use {
            val parsedResponse = it.toJson()
            assertContainsAtLeast(expectedHeader, parsedResponse["headers"])
            assertContainsExactly(expectedParams, parsedResponse["args"])
            assertThat(it.code()).isEqualTo(200)
        }
    }

    @Test
    fun `delete request with explicitly using HttpPatchContext and http delete method should throw exception`() {
        assertThatThrownBy {
            http(method = Method.DELETE, init = fun HttpPatchContext.() {
                host = "postman-echo.com"
                path = "/delete"

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
            })
        }.isInstanceOf(ClassCastException::class.java)
    }

    @Test
    fun `delete request with explicitly using HttpPutContext and http delete method should throw exception`() {
        assertThatThrownBy {
            http(method = Method.DELETE, init = fun HttpPutContext.() {
                host = "postman-echo.com"
                path = "/delete"

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
            })
        }.isInstanceOf(ClassCastException::class.java)
    }

}
