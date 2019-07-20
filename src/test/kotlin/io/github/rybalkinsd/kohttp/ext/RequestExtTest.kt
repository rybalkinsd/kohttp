package io.github.rybalkinsd.kohttp.ext

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
    fun `build curl command of get request with header and body`() {
        val request = Request.Builder().url("https://postman-echo.com/get").build()
        val actual = request.buildCurlCommand()

        assertEquals("curl -X GET \"https://postman-echo.com/get\"", actual)
    }
}
