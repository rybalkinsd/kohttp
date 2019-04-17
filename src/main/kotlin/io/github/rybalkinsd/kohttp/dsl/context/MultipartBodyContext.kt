package io.github.rybalkinsd.kohttp.dsl.context

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

/**
 *
 * @since 0.8.0
 * @author sergey
 */
@HttpDslMarker
class MultipartBodyContext(type: String?) {
    private val mediaType = type?.let { MediaType.get(it) }
    private val builder = MultipartBody.Builder().also { builder ->
        mediaType?.let { builder.setType(mediaType) }
    }

    operator fun FormDataPart.unaryPlus() {
        builder.addFormDataPart(first, second, third)
    }

    fun form(name: String, file: File): FormDataPart
        = FormDataPart(name, file.name, RequestBody.create(null, file))

    fun form(name: String, filename: String, content: ByteArray): FormDataPart
        = FormDataPart(name, filename, RequestBody.create(null, content))

    fun build(): MultipartBody = builder.build()
}

typealias FormDataPart = Triple<String, String?, RequestBody>
