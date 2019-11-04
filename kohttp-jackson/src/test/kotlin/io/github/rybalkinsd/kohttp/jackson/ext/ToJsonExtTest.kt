package io.github.rybalkinsd.kohttp.jackson.ext

import com.fasterxml.jackson.core.JsonParseException
import io.github.rybalkinsd.kohttp.ext.httpGet
import io.github.rybalkinsd.kohttp.util.json
import io.mockk.every
import io.mockk.mockk
import okhttp3.Response
import okhttp3.ResponseBody
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.Test

/**
 * @author sergey
 */
class ToJsonExtTest {
    private val getUrl = "https://postman-echo.com/get"


    @Test
    fun `gets response as json # ext`() {
        val response = getUrl.httpGet().toJson().toString()
        val expectedRegex = json {
            "args" to json { }
            "headers" to json {
                "x-forwarded-proto" to "https"
                "host" to "postman-echo.com"
                "accept-encoding" to "gzip"
                "user-agent" to "okhttp/[0-9]*.[0-9]*.[0-9]*"
                "x-forwarded-port" to "443"
            }
            "url" to getUrl
        }.escape()

        assertThat(response).matches(expectedRegex)
    }

    @Test
    fun `null body toJson`() {
        val response = mockk<Response>()
        every { response.body() } returns null

        assertThatExceptionOfType(DeserializationException::class.java).isThrownBy {
            response.toJson()
        }
    }

    @Test
    fun `null body toJsonOrNull`() {
        val response = mockk<Response>()
        every { response.body() } returns null

        assertThat(response.toJsonOrNull()).isNull()
    }

    @Test
    fun `empty body toJson`() {
        val response = mockk<Response>()
        val body = mockk<ResponseBody>()
        every { response.body()} returns body
        every { body.string() } returns ""

        assertThatExceptionOfType(DeserializationException::class.java).isThrownBy {
            response.toJson()
        }
    }

    @Test
    fun `empty body toJsonOrNull`() {
        val response = mockk<Response>()
        val body = mockk<ResponseBody>()
        every { response.body()} returns body
        every { body.string() } returns ""

        assertThat(response.toJsonOrNull()).isNull()
    }

    @Test
    fun `broken body toJson`() {
        val response = mockk<Response>()
        val body = mockk<ResponseBody>()
        every { response.body()} returns body
        every { body.string() } returns "{ 'a': 42"

        assertThatExceptionOfType(JsonParseException::class.java).isThrownBy {
            response.toJson()
        }
    }

    @Test
    fun `broken body toJsonOrNull`() {
        val response = mockk<Response>()
        val body = mockk<ResponseBody>()
        every { response.body()} returns body
        every { body.string() } returns "{ 'a': 42"

        assertThat(response.toJsonOrNull()).isNull()
    }

    @Test
    fun `valid body toJson`() {
        val response = mockk<Response>()
        val body = mockk<ResponseBody>()
        every { response.body()} returns body
        every { body.string() } returns """{ "a": 42 }"""

        with(response.toJson()) {
            @Suppress("UsePropertyAccessSyntax")
            assertThat(this.isNull).isFalse()
            assertThat(this["a"].intValue()).isEqualTo(42)

        }
    }

    @Test
    fun `valid body toJsonOrNull`() {
        val response = mockk<Response>()
        val body = mockk<ResponseBody>()
        every { response.body()} returns body
        every { body.string() } returns """{ "a": 42 }"""

        with(response.toJsonOrNull()) {
            @Suppress("UsePropertyAccessSyntax")
            assertThat(this).isNotNull()
            assertThat(this!!["a"].intValue()).isEqualTo(42)

        }
    }



}

private fun String.escape(): String = replace("/", "\\/")
        .replace("{", "\\{")
        .replace("}", "\\}")
