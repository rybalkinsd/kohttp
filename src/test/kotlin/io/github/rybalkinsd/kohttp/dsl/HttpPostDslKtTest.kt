package io.github.rybalkinsd.kohttp.dsl

import io.github.rybalkinsd.kohttp.util.asJson
import org.junit.Test
import java.lang.IllegalArgumentException
import kotlin.test.assertEquals

/**
 * Created by Sergey on 23/07/2018.
 */
class HttpPostDslKtTest {

    @Test
    fun `post request with form # postman echo`() {
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

            body {
                form {
                    "login" to "user"
                    "email" to "john.doe@gmail.com"
                }
            }
        }.use {
            println(it.body()?.string())
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
            println(it.body()?.string())
        }
    }

    @Test
    fun `post request with json # postman echo`() {
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
            println(it.body()?.string())
        }
    }

    @Test
    fun `post request with json string # postman echo`() {
        val response = httpPost {
            host = "postman-echo.com"
            path = "/post"

            body {
                content("application/json") {
                    """{"login":"user","email":"john.doe@gmail.com"}"""
                }
            }
        }

        response.use {
            println(it.body()?.string())
        }
    }

    @Test
    fun `post request with json string 2 # postman echo`() {
        val response = httpPost {
            host = "postman-echo.com"
            path = "/post"

            body {
                json(
                    """{
                        "login":"user",
                        "email":"john.doe@gmail.com"
                    }"""
                )
            }
        }

        response.use {
            println(it.body()?.string())
        }
    }

    @Test
    fun `post request with file # postman echo`() {
        val response = httpPost {
            host = "postman-echo.com"
            path = "/post"

            body {
                content("image/gif") {
                    this::class.java.classLoader.getResource("cat.gif").file
                }
            }
        }

        response.use {
            println(it.body()?.string())
        }
    }

    @Test
    fun `post request with byte array # postman echo`() {
        val response = httpPost {
            host = "postman-echo.com"
            path = "/post"

            body {
                content("image/gif") {
                    """{"login":"user","email":"john.doe@gmail.com"}""".toByteArray()
                }
            }
        }

        response.use {
            println(it.body()?.string())
        }
    }

    @Test
    fun `post request with wrong type # postman echo`() {
        try {
            httpPost {
                host = "postman-echo.com"
                path = "/post"
                body {
                    content("image/gif") {
                        this
                    }
                }
            }
            assert(false)
        } catch (e: IllegalArgumentException) {
            assert(true)
        }
    }

    @Test
    fun `post request with empty body# postman echo`() {
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
            assertEquals(0, it.body()?.string().asJson()["headers"]["content-length"].asInt())
        }
    }
}
