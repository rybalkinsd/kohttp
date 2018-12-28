package io.github.rybalkinsd.kohttp.dsl

import io.github.rybalkinsd.kohttp.util.asJson
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertTrue

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

            body("application/json") {
                string("""{"login":"user","email":"john.doe@gmail.com"}""")
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
                json("""{"login":"user","email":"john.doe@gmail.com"}""")
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

            body("image/gif") {
                val path = this::class.java.classLoader.getResource("cat.gif").file
                // both are fine
                file(path)
                file(File(path))
            }
        }

        response.use {
            with(it.body()?.string()) {
                println(this)
                assertTrue { asJson()["headers"]["content-length"].asLong() > 100_000 }
            }
        }
    }

    @Test
    fun `post request with byte array # postman echo`() {
        val response = httpPost {
            host = "postman-echo.com"
            path = "/post"

            body {
                bytes("Blablabla".toByteArray())
            }
        }

        response.use {
            println(it.body()?.string())
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
