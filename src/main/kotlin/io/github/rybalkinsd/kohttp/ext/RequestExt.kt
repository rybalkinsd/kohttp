package io.github.rybalkinsd.kohttp.ext

import okhttp3.Headers
import okhttp3.Request
import okhttp3.RequestBody
import okio.Buffer
import java.nio.charset.Charset

/**
 *  Build curl command of the request
 *
 * @author doyaaaaaken
 */
internal fun Request.buildCurlCommand(): String {
    return buildString {
        append("curl -X ${method()}")
        append(buildCurlHeaderOption(headers()))
        append(buildCurlBodyOption(body()))
        append(" \"${url()}\"")
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
