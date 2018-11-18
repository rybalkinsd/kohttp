package io.github.rybalkinsd.kohttp.dsl

import io.github.rybalkinsd.kohttp.dsl.async.asyncHttpGet
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals

class AsyncHttpGetDslTest {

    @Test
    fun name() {
        val response = asyncHttpGet {
            host = "postman-echo.com"
            path = "/get"

            header {
                "one" to 42
                "two" to "123"
            }

            param {
                "text" to "iphone"
                "lr" to 213
            }
        }
        runBlocking {
            response.await().use {
                print(it.body()?.string())
                assertEquals(200, it.code())
            }
        }
    }
}
