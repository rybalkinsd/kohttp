package com.kohttp.dsl

import com.kohttp.util.json
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

            body(mediaType = MediaTypes.JSON) {
                "a" to "b"
                "c" to json {
                    "x" to "y"
                }
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

        }.also {
            assertNotNull(it)
            println(it?.body()?.string())
        }
    }
}