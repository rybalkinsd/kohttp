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
        val url = httpUrlFor { url("https://www.example.org:8080/path?foo=bar") }
        assertEquals("https", url.scheme())
        assertEquals("www.example.org", url.host())
        assertEquals("/path", url.encodedPath())
        assertEquals(8080, url.port())
        assertEquals("foo=bar", url.query())
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
    fun `empty query`() {
        val url = httpUrlFor { url("https://www.example.org/?") }
        assertEquals(null, url.query())
    }

    @Test
    fun `query with malformed path`() {
        val url = httpUrlFor { url("https://www.example.org/?a=b") }
        assertEquals("a=b", url.query())
    }

    @Test
    fun `query added from 2 sources`() {
        val url = httpUrlFor {
            url("https://www.example.org/path?a=xxx&a=")
            param {
                "a" to "yyy"
            }
        }
        assertEquals("a=xxx&a=&a=yyy", url.query())
    }

    @Test
    fun `duplicate query keys`() {
        val url = httpUrlFor { url("https://www.example.org/path?id[]=1&id[]=2") }
        assertEquals("id[]=1&id[]=2", url.query())
    }

    @Test(expected = IllegalArgumentException::class)
    fun `query without value`() {
        HttpGetContext().apply { url("https://www.example.org/path?foo") }
    }

    private fun httpUrlFor(init: HttpContext.() -> Unit): HttpUrl =
        HttpGetContext()
            .run {
                init()
                makeUrl().build()
            }
}
