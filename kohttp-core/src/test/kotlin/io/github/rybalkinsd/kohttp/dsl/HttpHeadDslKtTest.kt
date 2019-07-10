package io.github.rybalkinsd.kohttp.dsl

import io.github.rybalkinsd.kohttp.util.json
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * @author sergey
 */
class HttpHeadDslKtTest {

    @Test
    fun `single sync http head invocation with param`() {
        val response = httpHead {
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
    fun `single sync http head invocation with param and header`() {
        val response = httpHead {
            host = "postman-echo.com"
            path = "/head"

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
        }

        response.use {
            assertTrue { it.body()!!.string().isEmpty() }
            assertEquals(200, it.code())
        }
    }
}