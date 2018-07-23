package com.kohttp.dsl

import com.kohttp.client.CommonHttpClient
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response


/**
 * Created by Sergey on 23/07/2018.
 */
fun httpPost(init: HttpPostContext.() -> Unit): Response? {
    val context = HttpPostContext().also(init)
    val url = context.toHttpUrlBuilder().build()

    val request = Request.Builder()
            .url(url)
            .post(context.body)
            .build()

    return CommonHttpClient.newCall(request).execute()
}

class HttpPostContext: HttpContext() {
    internal lateinit var body: RequestBody

    fun form(init: FormContext.() -> Unit) {
        body = FormContext().also(init).toRequestBody()
    }
}

class FormContext {
    private val content: MutableMap<String, Any> = mutableMapOf()

    infix fun String.to(v: Any) {
        content[this] = v
    }

    internal fun toRequestBody() = RequestBody.create(MediaTypes.X_WWW_FORM_URLENCODED.type,
            content.map { "${it.key}=${it.value}" }
                   .joinToString(separator = "&")
    )
}

enum class MediaTypes(val type: MediaType) {
    X_WWW_FORM_URLENCODED(MediaType.get("application/x-www-FormContext-urlencoded"))
}