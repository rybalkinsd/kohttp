package com.kohttp.dsl

import okhttp3.HttpUrl

/**
 * Created by Sergey Rybalkin on 23/07/2018.
 */
open class HttpContext {
    val paramContext: ParamContext = ParamContext()
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

open class ParamContext {
    private val content: MutableMap<String, Any> = mutableMapOf()

    infix fun String.to(v: Any) {
        content[this] = v
    }

    internal fun forEach(action: (k: String, v: Any) -> Unit) = content.forEach(action)
}
