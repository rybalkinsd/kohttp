package com.kohttp.dsl

import org.junit.Test

/**
 * Created by Bpaxio on 06/09/2018.
 */
class HttpPatchDslKtTest {

    @Test
    fun `patch request with form # postman echo`() {
        httpPatch {
            host = "postman-echo.com"
            path = "/patch"

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
    fun `patch request with json # postman echo`() {
        val response = httpPatch {
            host = "postman-echo.com"
            path = "/patch"

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
}