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
    /**
     * Sets URL for File Upload.
     * @param url: `java.net.URL` destination for upload
     * @return Unit.
     */
    fun url(url: URL) {
        context.url(url)
    }

    /**
     * Sets URL for File Upload.
     * @param url: `String` destination for upload
     * @return Unit.
     */
    fun url(url: String) {
        context.url(url)
    }

    /**
     * Sets File to Upload.
     * @param content: `java.io.File` file to upload
     * @return Unit.
     */
    fun file(content: File) {
        context.multipartBody {
            +form("file", content)
        }
    }

    /**
     * Sets File to Upload.
     * @param content: `java.net.URI` for file to upload
     * @return Unit.
     */
    fun file(content: URI) {
        file(File(content))
    }

    /**
     * Sets File to Upload.
     * @param filename: `String`
     * @param content: `ByteArray` file contents
     * @return Unit.
     */
    fun bytes(filename: String, content: ByteArray) {
        context.multipartBody {
            +form("file", filename, content)
        }
    }
}
