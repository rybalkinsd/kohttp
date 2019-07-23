package io.github.rybalkinsd.kohttp.ext

import okhttp3.FormBody
import okhttp3.Request
import org.junit.Test
import kotlin.test.assertEquals

/**
 * @author doyaaaaaken
 */
class RequestExtTest {

    @Test
    fun `build curl command of simple get request`() {
        val request = Request.Builder().url("https://postman-echo.com/get").build()
        val actual = request.buildCurlCommand()

        assertEquals("curl -X GET \"https://postman-echo.com/get\"", actual)
    }

    @Test
    fun `build curl command of request with header`() {
        val request = Request.Builder()
                .url("https://postman-echo.com/get")
                .header("Cookie", "foo=6; bar=28")
                .header("Content-Type", "application/json")
                .build()
        val actual = request.buildCurlCommand()

        assertEquals("curl -X GET -H \"Cookie: foo=6; bar=28\" -H \"Content-Type: application/json\" \"https://postman-echo.com/get\"", actual)
    }

    @Test
    fun `build curl command of request with body`() {
        val request = Request.Builder()
                .url("https://postman-echo.com/post")
                .post(FormBody.Builder().add("foo", "123").add("bar", "abc").build())
                .build()
        val actual = request.buildCurlCommand()

        assertEquals("curl -X POST --data \$'foo=123&bar=abc' \"https://postman-echo.com/post\"", actual)
    }
}
