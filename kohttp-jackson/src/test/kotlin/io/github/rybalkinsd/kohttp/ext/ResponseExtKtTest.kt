package io.github.rybalkinsd.kohttp.ext

import io.github.rybalkinsd.kohttp.util.json
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

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
}

private fun String.escape(): String = replace("/", "\\/")
        .replace("{", "\\{")
        .replace("}", "\\}")
