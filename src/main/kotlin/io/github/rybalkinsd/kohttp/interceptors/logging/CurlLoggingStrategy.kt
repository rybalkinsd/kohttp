package io.github.rybalkinsd.kohttp.interceptors.logging

import io.github.rybalkinsd.kohttp.ext.asSequence
import okhttp3.Headers
import okhttp3.Request
import okhttp3.RequestBody
import okio.Buffer
import java.nio.charset.Charset

/**
 * Logging strategy as curl command format
 *
 * @author doyaaaaaken
 */
class CurlLoggingStrategy : LoggingStrategy {

    override fun log(request: Request, logging: (String) -> Unit) {
        val command = buildCurlCommand(request)
        logging("╭--- cURL command ---")
        logging(command)
        logging("╰--- (copy and paste the above line to a terminal)")
    }

    fun buildCurlCommand(request: Request): String {
        return buildString {
            append("curl -X ${request.method()}")
            append(buildCurlHeaderOption(request.headers()))
            append(buildCurlBodyOption(request.body()))
            append(" \"${request.url()}\"")
        }
    }

    private fun buildCurlHeaderOption(headers: Headers): String {
        return headers.asSequence().map { (name, value) ->
            val trimmedValue = value.trimDoubleQuote()
            " -H \"$name: $trimmedValue\""
        }.joinToString("")
    }

    private fun buildCurlBodyOption(body: RequestBody?): String {
        if (body == null) return ""

        val buffer = Buffer().apply { body.writeTo(this) }
        val utf8 = Charset.forName("UTF-8")
        val charset = body.contentType()?.charset(utf8) ?: utf8
        return " --data $'" + buffer.readString(charset).replace("\n", "\\n") + "'"
    }

    private fun String.trimDoubleQuote(): String {
        return if (startsWith('"') && endsWith('"')) {
            substring(1, length - 1)
        } else {
            this
        }
    }
}
