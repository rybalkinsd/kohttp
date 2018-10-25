package com.kohttp.dsl

import org.junit.Test

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
            println(it.body()?.string())
        }
    }
}