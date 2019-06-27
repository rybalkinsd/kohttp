package io.github.rybalkinsd.kohttp.ext

import io.github.rybalkinsd.kohttp.dsl.context.HttpContext
import io.github.rybalkinsd.kohttp.dsl.context.HttpGetContext
import okhttp3.HttpUrl
import org.junit.Test
import java.net.MalformedURLException
import java.net.URL
import kotlin.test.assertEquals
import kotlin.test.assertNull

class HttpContextExtKtTest {

    @Test
    fun `full url test`() {
        val context = HttpGetContext().apply { url("https://www.example.org:8080/path?foo=bar") }
        assertEquals("https", context.scheme)
        assertEquals("www.example.org", context.host)
        assertEquals("/path", context.path)
        assertEquals("bar", context.params["foo"])
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

    @Test
    fun `url with empty query`() {
        val url = httpUrlFor { url("https://www.example.org/?") }
        assertEquals(null, url.query())
    }

    @Test
    fun `url with single param`() {
        val context = HttpGetContext().apply {
            url("http://www.example.org/path/?a=b")
        }

        val params = context.params
        assertEquals(1, params.size)
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
            url("http://www.example.org/path?a&a=&a=123&a=xxx#label")
        }

        val params = context.params
        assertEquals(1, params.size)
        assertEquals(listOf("", "", "123", "xxx"), params["a"])
    }

    @Test
    fun `query added from 2 sources`() {
        val context = HttpGetContext().apply {
            url("https://www.example.org/path?a=xxx&a=")
            param {
                "a" to "yyy"
            }
        }

        val params = context.params
        assertEquals(1, params.size)
        assertEquals(listOf("xxx", "", "yyy"), params["a"])
    }

    @Test
    fun `duplicate query keys`() {
        val url = httpUrlFor { url("https://www.example.org/path?id[]=1&id[]=2") }
        assertEquals("id[]=1&id[]=2", url.query())
    }


    @Test
    fun `query param without value`() {
        val context = HttpGetContext().apply { url("https://www.example.org/path?foo&bar=baz") }
        with(context.params) {
            assertEquals(2, size)
            assertEquals("", get("foo"))
            assertEquals("baz", get("bar"))
        }
    }

    private fun httpUrlFor(init: HttpContext.() -> Unit): HttpUrl =
        HttpGetContext().run {
            init()
            makeUrl().build()
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


