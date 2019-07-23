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
        append("curl -X ${method()}")

        //headers
        val headers = headers()
        for (i in 0 until headers.size()) {
            val name = headers.name(i)
            var value = headers.value(i)
            if (value[0] == '"' && value[value.length - 1] == '"') {
                value = "\\\"" + value.substring(1, value.length - 1) + "\\\""
            }
            if ("Accept-Encoding".equals(name, ignoreCase = true) && "gzip".equals(value, ignoreCase = true)) {
                compressed = true
            }
            append(" -H \"$name: $value\"")
        }

        //body
        body()?.let {
            val buffer = Buffer().apply { it.writeTo(this) }
            val utf8 = Charset.forName("UTF-8")
            val charset = it.contentType()?.charset(utf8) ?: utf8
            append(" --data $'" + buffer.readString(charset).replace("\n", "\\n") + "'")
        }

        if (compressed) {
            append(" --compressed")
        }
        append(" \"${url()}\"")
    }.toString()
}

