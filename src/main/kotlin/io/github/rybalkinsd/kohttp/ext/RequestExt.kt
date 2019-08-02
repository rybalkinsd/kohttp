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
    return headers.asSequence().map { header ->
        val value = header.value.let { v ->
            if (v.startsWith('"') && v.endsWith('"')) {
                v.substring(1, v.length - 1)
            } else {
                v
            }
        }
        " -H \"${header.name}: $value\""
    }.joinToString("")
}

private fun buildCurlBodyOption(body: RequestBody?): String {
    return if (body == null) {
        ""
    } else {
        val buffer = Buffer().apply { body.writeTo(this) }
        val utf8 = Charset.forName("UTF-8")
        val charset = body.contentType()?.charset(utf8) ?: utf8
        " --data $'" + buffer.readString(charset).replace("\n", "\\n") + "'"
    }
}
