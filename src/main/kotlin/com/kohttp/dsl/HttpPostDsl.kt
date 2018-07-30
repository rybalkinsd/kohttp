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
            .addHeader("content-type", MediaTypes.JSON.type.toString())
            .post(context.body)
            .build()

    return CommonHttpClient.newCall(request).execute()
}

class HttpPostContext: HttpContext() {
    internal lateinit var body: RequestBody

    fun body(mediaType: MediaTypes = MediaTypes.X_WWW_FORM_URLENCODED, init: FormContext.() -> Unit) {
        body = FormContext().also(init).toRequestBody()
    }
}

class FormContext {
    private val content: MutableList<Pair<String, Any?>> = mutableListOf()

    infix fun String.to(v: Any) {
        content += Pair(this, v)
    }

    internal fun toRequestBody() = RequestBody.create(MediaTypes.JSON.type,
            content.joinToString(separator = ",", prefix = "{", postfix = "}") { "${it.first}=${it.second}" }
    )
}

enum class MediaTypes(val type: MediaType) {
    X_WWW_FORM_URLENCODED(MediaType.get("application/x-www-FormContext-urlencoded")),
    JSON(MediaType.get("application/json"))
}