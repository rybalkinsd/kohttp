package io.github.rybalkinsd.kohttp.dsl.context

import io.github.rybalkinsd.kohttp.util.Form
import io.github.rybalkinsd.kohttp.util.Json
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File


/**
 *
 * @since 0.1.0
 * @author sergey, alex
 */
@HttpDslMarker
open class BodyContext(type: String?) {
    private val mediaType = type?.let { it.toMediaType() }

    fun string(content: String): RequestBody = content.toRequestBody(mediaType)
    fun file(content: File): RequestBody = content.asRequestBody(mediaType)
    fun bytes(content: ByteArray): RequestBody = content.toRequestBody(mediaType)

    fun json(content: String): RequestBody = content.toRequestBody(JSON)
    fun form(content: String): RequestBody = content.toRequestBody(FORM)

    fun json(init: Json.() -> Unit): RequestBody = Json().also(init).toString().toRequestBody(JSON)
    fun form(init: Form.() -> Unit): RequestBody = Form().also(init).makeBody()
}

private val JSON = "application/json".toMediaType()
private val FORM = "application/x-www-form-urlencoded".toMediaType()
