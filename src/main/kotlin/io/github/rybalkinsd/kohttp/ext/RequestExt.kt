package io.github.rybalkinsd.kohttp.ext

import okhttp3.Request
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

        //headers
        headers().asSequence().forEach { header ->
            val value = if (header.value.firstOrNull() == '"' && header.value.lastOrNull() == '"') {
                """\"${header.value.substring(1, header.value.length - 1)}\""""
            } else {
                header.value
            }
            append(" -H \"${header.name}: $value\"")
        }

        //body
        body()?.let {
            val buffer = Buffer().apply { it.writeTo(this) }
            val utf8 = Charset.forName("UTF-8")
            val charset = it.contentType()?.charset(utf8) ?: utf8
            append(" --data $'" + buffer.readString(charset).replace("\n", "\\n") + "'")
        }

        append(" \"${url()}\"")
    }
}

