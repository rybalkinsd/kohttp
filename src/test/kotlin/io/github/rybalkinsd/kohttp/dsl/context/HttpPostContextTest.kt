package io.github.rybalkinsd.kohttp.dsl.context

import org.junit.Test
import kotlin.test.assertEquals

/**
 * @author mlevesquedion
 */
class HttpPostContextTest {
    @Test
    fun `when body is form content type should be x-www-form-urlencoded`() {
        val expectedContentType = "application/x-www-form-urlencoded; charset=utf-8"
        val context = HttpPostContext()
        context.body { form { } }
        assertEquals(expectedContentType, context.makeBody().contentType().toString())
    }

    @Test
    fun `when body is json content type should be json`() {
        val expectedContentType = "application/json; charset=utf-8"
        val context = HttpPostContext()
        context.body { json { } }
        assertEquals(expectedContentType, context.makeBody().contentType().toString())
    }

    @Test
    fun `should set content type from header`() {
        val contentType = "application/xml; charset=utf-8"
        val context = HttpPostContext()
        context.header { "Content-Type" to contentType }
        context.body { string("content") }
        assertEquals(contentType, context.makeBody().contentType().toString())
    }

    @Test
    fun `should set custom content type from body`() {
        val contentType = "application/xml; charset=utf-8"
        val context = HttpPostContext()
        context.body(contentType) { string("content") }
        assertEquals(contentType, context.makeBody().contentType().toString())
    }

    @Test
    fun `should override custom content type with type inside body`() {
        val customContentType = "application/xml; charset=utf-8"
        val bodyContentType = "application/x-www-form-urlencoded; charset=utf-8"
        val context = HttpPostContext()
        context.body(customContentType) { form {} }
        assertEquals(bodyContentType, context.makeBody().contentType().toString())
    }

}

