package com.kohttp.dsl

import com.kohttp.util.json
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

/**
 * Created by Sergey on 22/07/2018.
 */
class HttpGetDslKtTest {

    @Test
    fun `single sync http get invocation with param`() {
        val response = httpGet {
            host = "yandex.ru"
            path = "/search"

            param {
                "text" to "iphone"
                "lr" to 213
            }
        }

        assertNotNull(response)
        assertEquals(200, response?.code())
    }

    @Test
    fun `single sync http get invocation with param and header`() {
        httpGet {
            host = "postman-echo.com"
            path = "/get"

            val variable = 123L

            header {
                "one" to 42
                "two" to variable
                "three" to json {
                    "a" to variable
                    "b" to json {
                        "b1" to "512"
                    }
                    "c" to listOf(1, 2.0, 3)
                }

                cookie {
                    "aaa" to "bbb"
                    "ccc" to 42
                }
            }

            param {
                "text" to "iphone"
                "lr" to 213
            }
        }.also {
            print(it?.body()?.string())
            assertEquals(200, it?.code())
        }
    }
}