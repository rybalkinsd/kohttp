package io.github.rybalkinsd.kohttp.jackson.dsl.context

import io.github.rybalkinsd.kohttp.dsl.httpPost
import io.github.rybalkinsd.kohttp.ext.url
import io.github.rybalkinsd.kohttp.jackson.ext.toJson
import org.junit.Test
import kotlin.test.assertEquals

/**
 * Created by Sergey Rybalkin on 2019-08-04.
 */
class HttpPostContextTest {
    @Test
    fun `header's content type is used on request when content type is set only on header (not set to body)`() {
        httpPost {
            url("https://postman-echo.com/post")
            header { "Content-Type" to "application/json; charset=utf-8" }
            body { string("content") }
        }.use {
            val res = it.toJson()
            assertEquals("application/json; charset=utf-8", res["headers"]["content-type"].asText())
        }
    }

    @Test
    fun `body's content type is used on request when content type is set on header and body`() {
        httpPost {
            url("https://postman-echo.com/post")
            header { "Content-Type" to "application/x-www-form-urlencoded; charset=utf-8" }
            body("application/json") { string("content") }
        }.use {
            val res = it.toJson()
            assertEquals("application/json; charset=utf-8", res["headers"]["content-type"].asText())
        }
    }

    @Test
    fun `inside body's content type is used on request when content type is set on body and inside body`() {
        httpPost {
            url("https://postman-echo.com/post")
            body("application/x-www-form-urlencoded") { json("content") }
        }.use {
            val res = it.toJson()
            assertEquals("application/json; charset=utf-8", res["headers"]["content-type"].asText())
        }
    }
}