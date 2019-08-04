package io.github.rybalkinsd.kohttp.dsl.context

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

}
