package io.github.rybalkinsd.kohttp.dsl.context

import io.github.rybalkinsd.kohttp.util.Form
import io.github.rybalkinsd.kohttp.util.Json
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File

@HttpDslMarker
class BodyContext(type: String?) {
    private val mediaType = type?.let { MediaType.get(it) }

    fun string(content: String): RequestBody = RequestBody.create(mediaType, content)
    fun file(content: File): RequestBody = RequestBody.create(mediaType, content)
    fun bytes(content: ByteArray): RequestBody = RequestBody.create(mediaType, content)

    fun json(content: String): RequestBody = JSON.create { content }
    fun form(content: String): RequestBody = FORM.create { content }

    fun json(init: Json.() -> Unit): RequestBody = JSON.create { Json().also(init).toString() }
    fun form(init: Form.() -> Unit): RequestBody = FORM.create { Form().also(init).toString() }

    private fun MediaType.create(contentProducer: () -> Any): RequestBody {
        return when (val content = contentProducer()) {
            is String -> RequestBody.create(this, content)
            is File -> RequestBody.create(this, content)
            is ByteArray -> RequestBody.create(this, content)
            else -> throw IllegalArgumentException("${content.javaClass.name} is not allowed as body content")
        }
    }
}

private val JSON = MediaType.get("application/json")
private val FORM = MediaType.get("application/x-www-form-urlencoded")
