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
        assertEquals(context.makeBody().contentType().toString(), expectedContentType)
    }

    @Test
    fun `when body is json content type should be json`() {
        val expectedContentType = "application/json; charset=utf-8"
        val context = HttpPostContext()
        context.body { json { } }
        assertEquals(context.makeBody().contentType().toString(), expectedContentType)
    }

    @Test
    fun `should accept custom content type`() {
        val contentType = "application/xml; charset=utf-8"
        val context = HttpPostContext()
        context.body(contentType) { string("content")  }
        assertEquals(context.makeBody().contentType().toString(), contentType)
    }

    @Test
    fun `when custom type is provided and body is form, content type should be form`() {
        val contentType = "application/xml; charset=utf-8"
        val expectedContentType = "application/x-www-form-urlencoded; charset=utf-8"
        val context = HttpPostContext()
        context.body(contentType) { form {}  }
        assertEquals(context.makeBody().contentType().toString(), expectedContentType)
    }

    @Test
    fun `when custom type is provided and body is json, content type should be json`() {
        val contentType = "application/xml; charset=utf-8"
        val expectedContentType = "application/json; charset=utf-8"
        val context = HttpPostContext()
        context.body(contentType) { json {}  }
        assertEquals(context.makeBody().contentType().toString(), expectedContentType)
    }
}

