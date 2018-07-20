package com.kohttp

import okhttp3.HttpUrl
import okhttp3.Request
import okhttp3.Response

/**
 * Created by Sergey Rybalkin on 21/07/2018.
 */
fun String.httpGet(): Response? {
    val request = Request.Builder()
            .url(this)
            .build()
    return CommonHttpClient.newCall(request).execute()
}

fun String.httpGet(init: HttpGetContext.() -> Unit): Response? {
    val context = HttpGetContext().apply(init)
    val url = context.toHttpUrlBuilder()
            .host(this)
            .scheme("https")
            .addEncodedPathSegment("search")
            .build()
    val request = Request.Builder()
            .url(url)
            .build()

    return CommonHttpClient.newCall(request).execute()
}

class HttpGetContext {
    private val paramContext: ParamContext = ParamContext()

    fun param(init: ParamContext.() -> Unit) {
        paramContext.init()
    }

    internal fun toHttpUrlBuilder() = HttpUrl.Builder().also {
        paramContext.forEach { k, v ->
            it.addQueryParameter(k, v.toString())
        }
    }
}

class ParamContext {
    internal val map: MutableMap<String, Any> = mutableMapOf()

    infix fun String.to(v: Any) {
        map[this] = v

    }

    fun forEach(action: (k: String, v: Any) -> Unit) = map.forEach(action)


}
