package io.github.rybalkinsd.kohttp.dsl.context

import java.io.File
import java.net.URL

class UploadContext(private val context: HttpPostContext = HttpPostContext()) : IHttpContext by context {
    fun url(url: URL) {
        context.url(url)
    }

    fun file(content: File) {
        context.multipartBody {
            +form("file", content)
        }
    }

    fun bytes(filename: String, content: ByteArray) {
        context.multipartBody {
            +form("file", filename, content)
        }
    }
}
