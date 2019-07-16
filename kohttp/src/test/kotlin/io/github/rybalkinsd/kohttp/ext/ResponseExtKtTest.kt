package io.github.rybalkinsd.kohttp.ext

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * @author sergey
 */
class ResponseExtKtTest {

    private val getUrl = "https://postman-echo.com/get"

    @Test
    fun `gets response as string # ext`() {
        val response = getUrl.httpGet().asString()!!
        val expectedRegex = """{
            |"args":{},
            |"headers":{
            |   "x-forwarded-proto":"https",
            |   "host":"postman-echo.com",
            |   "accept-encoding":"gzip",
            |   "user-agent":"okhttp/[0-9]*.[0-9]*.[0-9]*",
            |   "x-forwarded-port":"443"
            |   },
            |"url":"https://postman-echo.com/get"
            |}"""
                .trimMargin("|")
                .replace(Regex("\\s"),"")
                .escape()


        assertThat(response).matches(expectedRegex)
    }

    @Test
    fun `streams response # ext`() {
        val response = "https://postman-echo.com/stream/2".httpGet().asStream()
                ?.readBytes()?.let { String(it) }

        val expectedRegex = """{
        |  "args": {
        |    "n": "2"
        |  },
        |  "headers": {
        |    "x-forwarded-proto": "https",
        |    "host": "postman-echo.com",
        |    "accept-encoding": "gzip",
        |    "user-agent": "okhttp/[0-9]*.[0-9]*.[0-9]*",
        |    "x-forwarded-port": "443"
        |  },
        |  "url": "https://postman-echo.com/stream/2"
        |}{
        |  "args": {
        |    "n": "2"
        |  },
        |  "headers": {
        |    "x-forwarded-proto": "https",
        |    "host": "postman-echo.com",
        |    "accept-encoding": "gzip",
        |    "user-agent": "okhttp/[0-9]*.[0-9]*.[0-9*]",
        |    "x-forwarded-port": "443"
        |  },
        |  "url": "https://postman-echo.com/stream/2"
        |}"""
                .trimMargin("|")
                .escape()

        assertThat(response).matches(expectedRegex)
    }
}

private fun String.escape(): String = replace("/", "\\/")
        .replace("{", "\\{")
        .replace("}", "\\}")
