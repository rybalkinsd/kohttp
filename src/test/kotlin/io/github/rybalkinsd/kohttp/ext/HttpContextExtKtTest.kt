package io.github.rybalkinsd.kohttp.ext

import io.github.rybalkinsd.kohttp.dsl.context.HttpGetContext
import org.junit.Test
import java.net.MalformedURLException
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
}
