package io.github.rybalkinsd.kohttp.ext

import io.github.rybalkinsd.kohttp.dsl.context.HttpGetContext
import org.junit.Test
import java.net.MalformedURLException
import java.net.URL
import kotlin.test.assertEquals
import kotlin.test.assertNull

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
        HttpGetContext().apply { url(URL(null, null,0, "cat.gif")) }
    }


    @Test(expected = IllegalArgumentException::class)
    fun `not http or https protocol url`() {
        HttpGetContext().apply { url(URL("file", null,0, "cat.gif")) }
    }

    @Test(expected = IllegalArgumentException::class)
    fun `null host url`() {
        HttpGetContext().apply { url(URL("https", null,0, "cat.gif")) }
    }

}
