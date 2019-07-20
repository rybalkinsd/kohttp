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
    var compressed = false
    //TODO: test
    return StringBuilder().apply {
        append("curl")
        append(" -X ${method()}")

        //headers
        val headers = headers()
        for (i in 0 until headers.size()) {
            val name = headers.name(i)
            var value = headers.value(i)

            val start = 0
            val end = value.length - 1
            if (value[start] == '"' && value[end] == '"') {
                value = "\\\"" + value.substring(1, end) + "\\\""
            }

            if ("Accept-Encoding".equals(name, ignoreCase = true) && "gzip".equals(value, ignoreCase = true)) {
                compressed = true
            }
            append(" -H \"$name: $value\"")
        }

        //body
        body()?.let {
            val buffer = Buffer().apply { it.writeTo(this) }
            val UTF8 = Charset.forName("UTF-8")
            val charset = it.contentType()?.charset(UTF8) ?: UTF8
            // try to keep to a single line and use a subshell to preserve any line breaks
            append(" --data $'" + buffer.readString(charset).replace("\n", "\\n") + "'")
        }

        if (compressed) {
            append(" --compressed")
        }
        append(" \"${url()}\"")
    }.toString()
}
