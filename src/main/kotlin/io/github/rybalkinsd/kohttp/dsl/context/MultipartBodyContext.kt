package io.github.rybalkinsd.kohttp.dsl.context

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

@HttpDslMarker
class MultipartBodyContext(type: String?) {
    private val mediaType = type?.let { MediaType.get(it) }
    private val builder = MultipartBody.Builder()

    operator fun Triple<String, String?, RequestBody>.unaryPlus() {
        builder.addFormDataPart(first, second, third)
    }

    fun form(name: String, file: File): Triple<String, String?, RequestBody>
        = Triple(name, file.name, RequestBody.create(null, file))

    operator fun RequestBody.unaryPlus() {
        builder.addPart(this)
    }

//    operator fun unaryPlus(name: String, value: String) {
//        builder.addFormDataPart(name, value)
//    }


    fun build(): MultipartBody = builder.build()
}

