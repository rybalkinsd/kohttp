package io.github.rybalkinsd.kohttp.dsl.context

import io.github.rybalkinsd.kohttp.util.Form
import io.github.rybalkinsd.kohttp.util.Json
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.create
import java.io.File


/**
 *
 * @since 0.1.0
 * @author sergey, alex
 */
@HttpDslMarker
open class BodyContext(type: String?) {
    private val mediaType = type?.let { MediaType.get(it) }

    fun string(content: String): RequestBody = create(mediaType, content)
    fun file(content: File): RequestBody = create(mediaType, content)
    fun bytes(content: ByteArray): RequestBody = create(mediaType, content)

    fun json(content: String): RequestBody = create(JSON, content)
    fun form(content: String): RequestBody = create(FORM, content)

    fun json(init: Json.() -> Unit): RequestBody = create(JSON, Json().also(init).toString())
    fun form(init: Form.() -> Unit): RequestBody = Form().also(init).makeBody()

    fun multipart(contentType: String? = null, init: MultipartBodyContext.() -> Unit): RequestBody =
        MultipartBodyContext(contentType).apply(init).build()

}

private val JSON = MediaType.get("application/json")
private val FORM = MediaType.get("application/x-www-form-urlencoded")
