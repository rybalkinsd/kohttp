package io.github.rybalkinsd.kohttp.dsl.context

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.github.rybalkinsd.kohttp.PostmanEchoResponse
import io.github.rybalkinsd.kohttp.dsl.httpPost
import io.github.rybalkinsd.kohttp.ext.url
import org.junit.Test
import kotlin.test.assertEquals

/**
 * @author doyaaaaaken
 */
class HttpPostContextTest {

    @Test
    fun `application json content type when content type is set on 'body' method`() {
        val expected = "application/json; charset=utf-8"
        val context = HttpPostContext()
        context.body("application/json") { string("content") }

        assertEquals(expected, context.makeBody().contentType().toString())
    }

    @Test
    fun `x-www-form-urlencoded content type when body is form`() {
        val expected = "application/x-www-form-urlencoded; charset=utf-8"
        val context = HttpPostContext()
        context.body { form("") }

        assertEquals(expected, context.makeBody().contentType().toString())
    }

    @Test
    fun `application json content type when body is json`() {
        val expected = "application/json; charset=utf-8"
        val context = HttpPostContext()
        context.body { json {} }

        assertEquals(expected, context.makeBody().contentType().toString())
    }

    @Test
    fun `content type on 'form' method inside body overrides it on 'body' method`() {
        val expected = "application/x-www-form-urlencoded; charset=utf-8"
        val context = HttpPostContext()
        context.body("application/json; charset=utf-8") { form("") }

        assertEquals(expected, context.makeBody().contentType().toString())
    }

    @Test
    fun `header's content type is used on request when content type is set only on header (not set to body)`() {
        httpPost {
            url("https://postman-echo.com/post")
            header { "Content-Type" to "application/json; charset=utf-8" }
            body { string("content") }
        }.use {
            val res = jacksonObjectMapper().readValue(it.body()?.string(), PostmanEchoResponse::class.java)
            assertEquals("application/json; charset=utf-8", res.headers["content-type"])
        }
    }

    @Test
    fun `body's content type is used on request when content type is set on header and body`() {
        httpPost {
            url("https://postman-echo.com/post")
            header { "Content-Type" to "application/x-www-form-urlencoded; charset=utf-8" }
            body("application/json") { string("content") }
        }.use {
            val res = jacksonObjectMapper().readValue(it.body()?.string(), PostmanEchoResponse::class.java)
            assertEquals("application/json; charset=utf-8", res.headers["content-type"])
        }
    }

    @Test
    fun `inside body's content type is used on request when content type is set on body and inside body`() {
        httpPost {
            url("https://postman-echo.com/post")
            body("application/x-www-form-urlencoded") { json("content") }
        }.use {
            val res = jacksonObjectMapper().readValue(it.body()?.string(), PostmanEchoResponse::class.java)
            assertEquals("application/json; charset=utf-8", res.headers["content-type"])
        }
    }
}
