package io.github.rybalkinsd.kohttp.jackson.ext

import io.github.rybalkinsd.kohttp.ext.httpGet
import io.github.rybalkinsd.kohttp.util.json
import io.mockk.every
import io.mockk.mockk
import okhttp3.Response
import okhttp3.ResponseBody
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import kotlin.test.assertTrue

/**
 * @author sergey
 */
class ResponseExtKtTest {

    private val getUrl = "https://postman-echo.com/get"

    @Test
    fun `gets response as json # ext`() {
        val response = getUrl.httpGet().asJson().toString()
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
    fun `return NullNode when response body is null `() {
        val response = mockk<Response>()
        every { response.body() } returns null
        val json = response.asJson()
        assertTrue { json.isNull }
    }

    @Test
    fun `return NullNode when response body is empty`() {
        val response = mockk<Response>()
        every { response.body() } returns ResponseBody.create(null, "")
        val json = response.asJson()
        assertTrue { json.isNull }
    }
}


private fun String.escape(): String = replace("/", "\\/")
        .replace("{", "\\{")
        .replace("}", "\\}")
