package io.github.rybalkinsd.kohttp.dsl.context

import okhttp3.MultipartBody
import okhttp3.RequestBody

@HttpDslMarker
class MultipartBodyContext(type: String?) : BodyContext(type) {
    val builder = MultipartBody.Builder()

    operator fun RequestBody.unaryPlus() {
        builder.addPart(this)
    }

//    operator fun unaryPlus(name: String, value: String) {
//        builder.addFormDataPart(name, value)
//    }


    fun build(): MultipartBody = builder.build()
}

