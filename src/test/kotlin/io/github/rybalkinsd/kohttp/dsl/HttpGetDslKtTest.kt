package io.github.rybalkinsd.kohttp.dsl

import io.github.rybalkinsd.kohttp.assertResponses
import io.github.rybalkinsd.kohttp.util.json
import org.junit.Test
import kotlin.test.assertEquals
import io.github.rybalkinsd.kohttp.util.asJson


/**
 * Created by Sergey on 22/07/2018.
 */
class HttpGetDslKtTest {

    @Test
    fun `single sync http get invocation with param`() {
        val response = httpGet {
            host = "yandex.ru"
            path = "/search"
            port = 80

            param {
                "text" to "iphone"
                "lr" to 213
            }
        }.also { println(it) }

        response.use {
            assertEquals(200, it.code())
        }
    }

    @Test
    fun `single sync http get invocation with list param`() {
        val response = httpGet {
            host = "yandex.ru"
            path = "/search"
            port = 80

            param {
                "text" to listOf("iphone", "not iphone")
                "lr" to 213
            }
        }.also { println(it) }

        response.use {
            assertEquals(false, it.request().url().query()?.contains("%5B"))
            assertEquals(200, it.code())
        }
    }


    @Test
    fun `single sync http get invocation with param and header`() {
        val variable = 123L
        val expectedHeader = hashMapOf(
                "one" to "42",
                "two" to variable.toString(),
                "three" to """{"a":$variable,"b":{"b1":"512"},"c":[1,2.0,3]}""",
                "cookie" to "aaa=bbb; ccc=42"
        )

        val expectedParams = hashMapOf(
                "text" to "iphone",
                "lr" to "213"
        )
        val response = httpGet {
            host = "postman-echo.com"
            path = "/get"

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
        }
        response.use {
            val parsedResponse = it.body()?.string().asJson()
            assertResponses(parsedResponse["headers"], expectedHeader)
            assertResponses(parsedResponse["args"], expectedParams)
            assertEquals(200, it.code())
        }
    }
}
