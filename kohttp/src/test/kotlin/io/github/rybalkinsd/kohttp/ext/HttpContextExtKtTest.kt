package io.github.rybalkinsd.kohttp.ext

import io.github.rybalkinsd.kohttp.dsl.context.HttpContext
import io.github.rybalkinsd.kohttp.dsl.context.HttpGetContext
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.net.MalformedURLException
import java.net.URL

class HttpContextExtKtTest {

    @Test
    fun `full url test`() {
        val context = HttpGetContext().apply { url("https://www.example.org:8080/path?foo=bar") }
        assertThat(context.scheme).isEqualTo("https")
        assertThat(context.host).isEqualTo("www.example.org")
        assertThat(context.path).isEqualTo("/path")
        assertThat(context.params["foo"]).isEqualTo("bar")
    }

    @Test
    fun `full url test without port`() {
        val context = HttpGetContext().apply { url("https://www.example.org/path") }
        assertThat(context.scheme).isEqualTo("https")
        assertThat(context.host).isEqualTo("www.example.org")
        assertThat(context.path).isEqualTo("/path")
        assertThat(context.port).isNull()
    }

    @Test(expected = MalformedURLException::class)
    fun `full url test without protocol`() {
        HttpGetContext().apply { url("www.example.org:8080/path") }
    }

    @Test
    fun `full url test without path`() {
        val context = HttpGetContext().apply { url("http://www.example.org") }
        assertThat(context.scheme).isEqualTo("http")
        assertThat(context.host).isEqualTo("www.example.org")
        assertThat(context.path).isEmpty()
        assertThat(context.port).isNull()
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
        val context = HttpGetContext().apply {
            url("https://www.example.org/?")
        }
        assertThat(context.params).isEmpty()
    }

    @Test
    fun `url with single param`() {
        val context = HttpGetContext().apply {
            url("http://www.example.org/path/?a=b")
        }

        val params = context.params
        assertThat(params).hasSize(1)
        assertThat(params["a"]).isEqualTo("b")
    }

    @Test
    fun `url with multiple params`() {
        val context = HttpGetContext().apply {
            url("http://www.example.org/path?a=b&c=&d=123#label")
        }

        val params = context.params
        assertThat(params).hasSize(3)
        assertThat(params["a"]).isEqualTo("b")
        assertThat(params["c"] as String).isEmpty()
        assertThat(params["d"]).isEqualTo("123")
    }

    @Test
    fun `url with multiple occurrences of parameter`() {
        val context = HttpGetContext().apply {
            url("http://www.example.org/path?a&a=&a=123&a=xxx#label")
        }

        val params = context.params
        assertThat(params).hasSize(1)
        assertThat(params["a"]).isEqualTo(listOf(null, "", "123", "xxx"))
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
        assertThat(params).hasSize(1)
        assertThat(params["a"]).isEqualTo(listOf("xxx", "", "yyy"))
    }

    @Test
    fun `duplicate query keys`() {
        val context = HttpGetContext().apply { url("https://www.example.org/path?id[]=1&id[]=2") }
        val params = context.params
        assertThat(params).hasSize(1)
        assertThat(params["id[]"]).isEqualTo(listOf("1", "2"))
    }


    @Test
    fun `query param without value`() {
        val context = HttpGetContext().apply { url("https://www.example.org/path?foo&bar=baz") }
        val params = context.params
        assertThat(params).hasSize(2)
        assertThat(params["foo"]).isNull()
        assertThat(params["bar"]).isEqualTo("baz")
    }

    @Test
    fun `query param with value containing = symbol`() {
        val context = HttpGetContext().apply { url("https://www.example.org/path?bool=2*2==4") }
        assertThat(context.params).hasSize(1)
        assertThat(context.params["bool"]).isEqualTo("2*2==4")
    }

    @Test
    fun `query with single null param`() {
        val context = HttpGetContext().apply {
            url("https://www.example.org/path?a")
        }

        assertThat(context.params).hasSize(1)
        assertThat(context.params["a"]).isNull()
    }

    private val HttpContext.params: Map<String, Any?>
        get() {
            val collector = mutableMapOf<String, Any?>()
            param {
                forEach { k, v -> collector[k] = v }
            }

            return collector
        }
}


