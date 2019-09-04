package io.github.rybalkinsd.kohttp.dsl

import io.github.rybalkinsd.kohttp.ext.url
import io.github.rybalkinsd.kohttp.jackson.ext.toJson
import io.github.rybalkinsd.kohttp.assertContainsAtLeast
import io.github.rybalkinsd.kohttp.assertContainsExactly
import io.github.rybalkinsd.kohttp.util.json
import org.junit.Test
import kotlin.test.assertEquals


/**
 * @author sergey, alex, gokul
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
        }

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
        }

        response.use {
            assertEquals(false, it.request().url().query()?.contains("%5B"))
            assertEquals(200, it.code())
        }
    }


    @Test
    fun `single sync http get invocation with param and header`() {
        val variable = 123L
        val expectedHeader = mapOf(
            "one" to "42",
            "two" to variable.toString(),
            "three" to """{"a":$variable,"b":{"b1":"512"},"c":[1,2.0,3]}""",
            "cookie" to "aaa=bbb; ccc=42"
        )

        val expectedParams = mapOf(
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
            val parsedResponse = it.toJson()
            assertContainsAtLeast(expectedHeader, parsedResponse["headers"])
            assertContainsExactly(expectedParams, parsedResponse["args"])
            assertEquals(200, it.code())
        }
    }

    @Test
    fun `single sync http get invocation with url`() {
        val variable = 123L
        val expectedHeader = mapOf(
            "one" to "42",
            "two" to variable.toString(),
            "three" to """{"a":$variable,"b":{"b1":"512"},"c":[1,2.0,3]}""",
            "cookie" to "aaa=bbb; ccc=42"
        )

        val expectedParams = mapOf(
            "text" to "iphone",
            "lr" to "213"
        )
        val response = httpGet {
            url("http://postman-echo.com/get")

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
            val parsedResponse = it.toJson()
            assertContainsAtLeast(expectedHeader, parsedResponse["headers"])
            assertContainsExactly(expectedParams, parsedResponse["args"])
            assertEquals(200, it.code())
        }
    }


    /**
     * @since 0.10.0
     */
    @Test
    fun `no omitting of query params in url`() {
        val response = httpGet {
            url("http://postman-echo.com/get?a=exists&b=exists")

            param {
                "c" to "exists"
            }
        }

        val expectedParams = mapOf(
            "a" to "exists",
            "b" to "exists",
            "c" to "exists"
        )

        response.use {
            val parsedResponse = it.toJson()
            assertContainsExactly(expectedParams, parsedResponse["args"])
        }
    }

    /**
     * @since 0.10.0
     */
    @Test
    fun `multiple param declaration with same key`() {
        val response = httpGet {
            url("http://postman-echo.com/get?a=1&a=")

            param {
                "a" to "3"
            }
        }

        val expectedParams = mapOf(
            "a" to listOf("1", "", "3")
        )


        response.use {
            val parsedResponse = it.toJson()
            assertContainsExactly(expectedParams, parsedResponse["args"])
        }
    }

    /**
     * @since 0.10.0
     */
    @Test
    fun `multiple param declaration in param block`() {
        val response = httpGet {
            url("http://postman-echo.com/get?a=1&a=&a")

            param {
                "a" to null
                "a" to "3"
                "a" to listOf(1, 2, null)
            }
        }

        val expectedParams = mapOf(
            "a" to listOf("1", "", null, null, "3", 1, 2, null)
        )


        response.use {
            val parsedResponse = it.toJson()
            assertContainsExactly(expectedParams, parsedResponse["args"])
        }
    }

}
