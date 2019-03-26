package io.github.rybalkinsd.kohttp.dsl.context

import okhttp3.MultipartBody
import okhttp3.RequestBody

@HttpDslMarker
class MultipartBodyContext(type: String?) : BodyContext(type) {
    private val builder = MultipartBody.Builder()

    fun body(contentType: String? = null, init: BodyContext.() -> RequestBody) {
        BodyContext(contentType).init()
    }

    operator fun RequestBody.unaryPlus() {
        builder.addPart(this)
    }

    fun build(): MultipartBody = builder.build()
}
