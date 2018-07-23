package com.kohttp.dsl

import org.junit.Test
import kotlin.test.assertNotNull

/**
 * Created by Sergey Rybalkin on 23/07/2018.
 */
class HttpPostDslKtTest {

    @Test
    fun `post request with form # postman echo`() {
        val response = httpPost {
            host = "postman-echo.com"
            path = "/post"

            param {
                "arg" to "iphone"
            }

            form {
                "a" to "b"
                "c" to 42
            }
        }.also {
            assertNotNull(it)
            println(it?.body()?.string())
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

            json {
                "a" to "b"
                "c" to 42
            }
        }.also {
            assertNotNull(it)
            println(it?.body()?.string())
        }
    }
}