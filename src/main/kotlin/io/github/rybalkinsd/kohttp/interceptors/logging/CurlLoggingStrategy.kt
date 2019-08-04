package io.github.rybalkinsd.kohttp.interceptors.logging

import io.github.rybalkinsd.kohttp.ext.asSequence
import okhttp3.Headers
import okhttp3.Request
import okhttp3.RequestBody
import okio.Buffer

/**
 * Logging strategy as curl command format
 *
 * @author doyaaaaaken
 */
class CurlLoggingStrategy : LoggingStrategy {

    override fun log(request: Request, logging: (String) -> Unit) {
        val command = buildCurlCommand(request)
        logging("--- cURL command ---")
        logging(command)
        logging("--------------------")
    }

    internal fun buildCurlCommand(request: Request) = buildString {
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
        }
        return " --data $'$data'"
    }

    private fun String.trimDoubleQuote() =
        if (startsWith('"') && endsWith('"'))
            substring(1, length - 1)
        else this
}
