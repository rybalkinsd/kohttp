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
    return CommonHttpClient.newCall(context.makeRequest()).execute()
}

class HttpGetContext {
    private val paramContext = ParamContext()
    private val headerContext = HeaderContext()
    var scheme: String = "http"
    var host: String = ""
    var port: Int? = null
    var path: String? = null

    fun param(init: ParamContext.() -> Unit) {
        paramContext.init()
    }

    fun header(init: HeaderContext.() -> Unit) {
        headerContext.init()
    }

    internal fun makeRequest(): Request = with(Request.Builder()) {
        url(makeUrl())
        headerContext.forEach { k, v ->
            addHeader(k, v.toString())
        }

        return build()
    }

    private fun makeUrl(): HttpUrl {
        with(HttpUrl.Builder()) {
            scheme(scheme)
            host(host)

            port?.let { port(it) }
            path?.let { encodedPath(it) }
            paramContext.forEach { k, v ->
                addQueryParameter(k, v.toString())
            }
            return build()
        }
    }
}

class HeaderContext {
    private val map: MutableMap<String, Any> = mutableMapOf()

    infix fun String.to(v: Any) {
        map[this] = v
    }

    fun cookie(init: CookieContext.() -> Unit) {
        map["cookie"] = CookieContext().also(init).collect()
    }

    internal fun forEach(action: (k: String, v: Any) -> Unit) = map.forEach(action)
}

class CookieContext {
    private val cookies: MutableList<Pair<String, Any>> = mutableListOf()

    infix fun String.to(v: Any) {
        cookies += Pair(this, v)
    }

    internal fun collect(): String = cookies.joinToString(separator = "; ") { (k, v) -> "$k=$v"}
}

class ParamContext {
    private val map: MutableMap<String, Any> = mutableMapOf()

    infix fun String.to(v: Any) {
        map[this] = v
    }

    internal fun forEach(action: (k: String, v: Any) -> Unit) = map.forEach(action)
}
