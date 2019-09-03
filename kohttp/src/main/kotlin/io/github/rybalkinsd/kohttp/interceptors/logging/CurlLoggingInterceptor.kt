package io.github.rybalkinsd.kohttp.interceptors.logging

import io.github.rybalkinsd.kohttp.ext.asSequence
import okhttp3.*
import okio.Buffer

/**
 * Request Logging Interceptor
 *
 * Logs cURL commands for outgoing requests.
 *
 * @param log log consumer
 *
 * -->
 * curl -X POST -H "one: 42" -H "cookie: aaa=bbb; ccc=42" \
 *      --data $'login=user&email=john.doe%40gmail.com' \
 *  "http://postman-echo.com/post?arg=iphone"
 * ---
 *
 * @since 0.11.0
 * @author doyaaaaaken, gokul, sergey
 */
class CurlLoggingInterceptor(
        private val log: (String) -> Unit = ::println
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        log("-->")
        val command = buildCurlCommand(request)
        log(command)
        log("---")

        return chain.proceed(request)
    }

    protected fun buildCurlCommand(request: Request) = buildString {
        append("curl -X ${request.method()}")
        append(buildCurlHeaderOption(request.headers()))
        append(buildCurlBodyOption(request.body()))
        append(""" "${request.url()}"""")
    }

    private fun buildCurlHeaderOption(headers: Headers): String {
        return headers.asSequence().map { (name, value) ->
            val trimmedValue = value.trimDoubleQuote()
            """ -H "$name: $trimmedValue""""
        }.joinToString("")
    }

    private fun buildCurlBodyOption(body: RequestBody?): String {
        if (body == null) return ""
        val data = Buffer().use {
            body.writeTo(it)
            it.readUtf8().replace("\n", "\\n")
                    .replace("\r", "\\r")
        }
        return " --data $'$data'"
    }

}

private fun String.trimDoubleQuote() =
        if (startsWith('"') && endsWith('"'))
            substring(1, length - 1)
        else this
