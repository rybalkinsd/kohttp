package io.github.rybalkinsd.kohttp.ext

import io.github.rybalkinsd.kohttp.dsl.context.HttpContext
import io.github.rybalkinsd.kohttp.dsl.context.HttpGetContext
import org.junit.Test
import java.net.MalformedURLException
import java.net.URL
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class HttpContextExtKtTest {

    @Test
    fun `full url test`() {
        val context = HttpGetContext().apply { url("https://www.example.org:8080/path") }
        assertEquals("https", context.scheme)
        assertEquals("www.example.org", context.host)
        assertEquals("/path", context.path)
        assertEquals(8080, context.port)
    }

    @Test
    fun `full url test without port`() {
        val context = HttpGetContext().apply { url("https://www.example.org/path") }
        assertEquals("https", context.scheme)
        assertEquals("www.example.org", context.host)
        assertEquals("/path", context.path)
        assertNull(context.port)
    }

    @Test(expected = MalformedURLException::class)
    fun `full url test without protocol`() {
        HttpGetContext().apply { url("www.example.org:8080/path") }
    }

    @Test
    fun `full url test without path`() {
        val context = HttpGetContext().apply { url("http://www.example.org") }
        assertEquals("http", context.scheme)
        assertEquals("www.example.org", context.host)
        assertEquals("", context.path)
        assertNull(context.port)
    }

    // expecting NPE for b/c of protocol
    @Test(expected = NullPointerException::class)
    fun `null protocol url`() {
        HttpGetContext().apply { url(URL(null, null, 0, "cat.gif")) }
    }


    @Test(expected = IllegalArgumentException::class)
    fun `not http or https protocol url`() {
        HttpGetContext().apply { url(URL("file", null, 0, "cat.gif")) }
    }

    @Test(expected = IllegalArgumentException::class)
    fun `null host url`() {
        HttpGetContext().apply { url(URL("https", null, 0, "cat.gif")) }
    }


//    path/?a=b"
//  "path?a=b"
//  "path/?a=b&c=&d=123"
//  "path?a=b#tag"
//  "path?a=xxx&a=&a=yyy
    @Test
    fun `url with single param`() {
        val context = HttpGetContext().apply {
            url("http://www.example.org/path?a=b")
        }

        val params = context.params
        assertEquals(1, params.size)
        assertTrue(params.containsKey("a"))
        assertEquals("b", params["a"])
    }

    @Test
    fun `url with multiple params`() {
        val context = HttpGetContext().apply {
            url("http://www.example.org/path?a=b&c=&d=123#label")
        }

        val params = context.params
        assertEquals(3, params.size)
        assertEquals("b", params["a"])
        assertEquals("", params["c"])
        assertEquals("123", params["d"])
    }

    @Test
    fun `url with multiple occurrences of parameter`() {
        val context = HttpGetContext().apply {
            url("http://www.example.org/path?a=&a=123&a=xxx#label")
        }

        val params = context.params
        assertEquals(1, params.size)
        assertEquals(listOf("", "123", "xxx"), params["a"])
    }

    private val HttpContext.params: Map<String, Any>
        get() {
            val collector = mutableMapOf<String, Any>()
            param {
                forEach { k, v -> collector[k] = v }
            }

            return collector
        }
}
