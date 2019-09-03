package io.github.rybalkinsd.kohttp.dsl.context

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody

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

    operator fun MultipartBody.Part.unaryPlus() {
        builder.addPart(this)
    }

    fun part(name: String, filename: String? = null, init: BodyContext.() -> RequestBody): MultipartBody.Part =
        MultipartBody.Part.createFormData(name, filename, BodyContext(null).init())


    fun build(): MultipartBody = builder.build()
}
