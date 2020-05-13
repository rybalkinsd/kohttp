package io.github.rybalkinsd.kohttp.ext

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.regex.Pattern

/**
 * @author sergey
 */
class ResponseExtKtTest {

    private val getUrl = "https://postman-echo.com/get"

    @Test
    fun `gets response as string # ext`() {
        val response = getUrl.httpGet().asString()!!

        assertThat(response)
                .containsPattern(""""user-agent":"okhttp/[0-9]*.[0-9]*.[0-9]*"""")
                .containsPattern(""""x-forwarded-proto":"https"""")
                .containsPattern(""""x-forwarded-port":"443"""")
                .containsPattern(""""host":"postman-echo.com"""")
                .containsPattern(""""accept-encoding":"gzip"""")
                .containsPattern(""""url":"https://postman-echo.com/get"""")
    }

    @Test
    fun `streams response # ext`() {
        val response = "https://postman-echo.com/stream/2".httpGet().asStream()
                ?.readBytes()?.let { String(it) }

        // Ensure we get two pairs of arguments as declared in our request
        assertThat(response).containsPattern(Pattern.compile("args.*args", Pattern.DOTALL))
    }
}
