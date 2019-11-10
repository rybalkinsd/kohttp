package io.github.rybalkinsd.kohttp.jackson.ext

import io.github.rybalkinsd.kohttp.dsl.httpGet
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.mockserver.client.MockServerClient
import org.mockserver.junit.MockServerRule
import org.mockserver.matchers.Times
import org.mockserver.model.HttpRequest
import org.mockserver.model.HttpResponse

/**
 * @author sergey
 */
class AsTypeExtTest {
    private val mockServerPort: Int = 1080
    private val localhost = "127.0.0.1"
    private val mockPath = "/mock"

    @Rule
    @JvmField
    var mockServerRule = MockServerRule(this, mockServerPort)


    @Test
    fun `simple class`() {
        respondWith("""
            { "a": 42 }
        """.trimIndent())

        mockGet().use {
            val simple: SimpleClass? = it.toType()
            assertThat(simple).isNotNull()
            assertThat(simple?.a).isEqualTo(42)
        }
    }

    @Test
    fun `null body`() {
        respondWith(null)

        mockGet().use {
            val simple: SimpleClass? = it.toType()
            assertThat(simple).isNull()
        }
    }

    @Test
    fun `empty body`() {
        respondWith("")

        mockGet().use {
            val simple: SimpleClass? = it.toType()
            assertThat(simple).isNull()
        }
    }

    @Test
    fun `complex constructor class`() {
        respondWith("""
            { 
                "port": $mockServerPort,
                "host": "$localhost"
            }
        """.trimIndent())

        mockGet().use {
            val complex = it.toType<ComplexConstructorClass>()
            assertThat(complex).isNotNull()
            assertThat(complex?.host).isEqualTo(localhost)
            assertThat(complex?.port).isEqualTo(mockServerPort)
        }
    }


    @Test
    fun `simple data class`() {
        respondWith("""
            { "a": 42 }
        """.trimIndent())

        mockGet().use {
            val simple: SimpleDataClass? = it.toType()
            assertThat(simple).isNotNull()
            assertThat(simple?.a).isEqualTo(42)
        }
    }

    @Test
    fun `nested data class`() {
        respondWith("""
            { 
                "x": 42,
                "y": null,
                "z": {
                    "a": 42
                }
            }
        """.trimIndent())

        mockGet().use {
            val nested: NestedDataClass? = it.toType()
            assertThat(nested).isNotNull()
            with (nested!!) {
                assertThat(x).isEqualTo(42)
                assertThat(y).isNull()
                assertThat(z.a).isEqualTo(42)
            }
        }
    }

    private fun mockGet() = httpGet {
        host = localhost
        port = mockServerPort
        path = mockPath
    }

    private fun respondWith(body: String?) {
        MockServerClient(localhost, mockServerPort)
                .`when`(
                        HttpRequest.request()
                                .withMethod("GET")
                                .withPath(mockPath),
                        Times.once()
                )
                .respond(
                        HttpResponse.response()
                                .withBody(body)
                                .withStatusCode(200)
                )
    }
}


data class SimpleDataClass(val a: Int)
data class NestedDataClass(val x: Int, val y: String?, val z: SimpleDataClass)

class SimpleClass {
    var a: Int = 0
}

class ComplexConstructorClass(val host: String) {
    var port: Int? = null
}