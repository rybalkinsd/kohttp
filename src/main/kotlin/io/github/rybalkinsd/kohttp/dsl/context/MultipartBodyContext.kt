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

    operator fun Triple<String, String?, RequestBody>.unaryPlus() {
        builder.addFormDataPart(first, second, third)
    }

    fun form(name: String, file: File): Triple<String, String?, RequestBody>
        = Triple(name, file.name, RequestBody.create(null, file))

    operator fun RequestBody.unaryPlus() {
        builder.addPart(this)
    }

    fun build(): MultipartBody = builder.build()
}
