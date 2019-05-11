package io.github.rybalkinsd.kohttp.dsl.context

import io.github.rybalkinsd.kohttp.ext.url
import java.io.File
import java.net.URI
import java.net.URL

/**
 *
 * @since 0.8.0
 * @author sergey
 */
class UploadContext(private val context: HttpPostContext = HttpPostContext()) : IHttpContext by context {

    fun param(init: ParamContext.() -> Unit) {
        context.param(init)
    }

    fun header(init: HeaderContext.() -> Unit) {
        context.header(init)
    }

    fun url(url: URL) {
        context.url(url)
    }

    fun url(url: String) {
        context.url(url)
    }

    fun file(content: File) {
        context.multipartBody {
            +form("file", content)
        }
    }

    fun file(content: URI) {
        file(File(content))
    }

    fun bytes(filename: String, content: ByteArray) {
        context.multipartBody {
            +form("file", filename, content)
        }
    }
}
