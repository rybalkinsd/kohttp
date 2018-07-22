package com.kohttp.dsl

import com.kohttp.client.CommonHttpClient
import okhttp3.HttpUrl
import okhttp3.Request
import okhttp3.Response

/**
 * Created by sergey on 22/07/2018.
 */

fun httpGet(init: HttpGetContext.() -> Unit): Response? {
    val context = HttpGetContext().apply(init)
    val url = context.toHttpUrlBuilder().build()
    val request = Request.Builder()
            .url(url)
            .build()

    return CommonHttpClient.newCall(request).execute()
}

class HttpGetContext {
    private val paramContext: ParamContext = ParamContext()
    var scheme: String = "http"
    var host: String = ""
    var port: Int? = null
    var path: String? = null

    fun param(init: ParamContext.() -> Unit) {
        paramContext.init()
    }

    internal fun toHttpUrlBuilder(): HttpUrl.Builder {
        with(HttpUrl.Builder()) {
            scheme(scheme)
            host(host)
            port?.let { port(it) }
            path?.let { encodedPath(it) }
            paramContext.forEach { k, v ->
                addQueryParameter(k, v.toString())
            }
            return this
        }
    }
}

class ParamContext {
    private val map: MutableMap<String, Any> = mutableMapOf()

    infix fun String.to(v: Any) {
        map[this] = v

    }

    internal fun forEach(action: (k: String, v: Any) -> Unit) = map.forEach(action)
}
