package com.kohttp.dsl

import com.kohttp.dsl.Method.DELETE
import com.kohttp.dsl.Method.GET
import com.kohttp.dsl.Method.HEAD
import com.kohttp.dsl.Method.PATCH
import com.kohttp.dsl.Method.POST
import com.kohttp.dsl.Method.PUT
import okhttp3.Headers
import okhttp3.HttpUrl
import okhttp3.Request
import okhttp3.RequestBody

/**
 * Other methods are not supported at the moment
 */
enum class Method {
    GET, POST, PUT, DELETE, PATCH, HEAD
}

internal interface IHttpContext {
    fun makeRequest(): Request
}

@DslMarker
annotation class HttpDslMarker

@HttpDslMarker
open class HttpContext(private val method: Method = GET) : IHttpContext {
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

    override fun makeRequest(): Request = with(Request.Builder()) {
        url(makeUrl().build())
        headers(makeHeaders().build())

        when (method) {
            POST, PUT, PATCH, DELETE -> method(method.name, makeBody())
            HEAD -> head()
        }

        return build()
    }

    open fun makeHeaders(): Headers.Builder = Headers.Builder().apply {
        headerContext.forEach { k, v ->
            add(k, v.toString())
        }
    }

    open fun makeUrl(): HttpUrl.Builder = HttpUrl.Builder().apply {
        scheme(scheme)
        host(host)
        port?.let { port(it) }
        path?.let { encodedPath(it) }
        paramContext.forEach { k, v ->
            addQueryParameter(k, v.toString())
        }
    }

    open fun makeBody(): RequestBody = throw UnsupportedOperationException("Request body is not supported for [$method] Method.")

}

@HttpDslMarker
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

@HttpDslMarker
class CookieContext {
    private val cookies: MutableList<Pair<String, Any>> = mutableListOf()

    infix fun String.to(v: Any) {
        cookies += Pair(this, v)
    }

    internal fun collect(): String = cookies.joinToString(separator = "; ") { (k, v) -> "$k=$v"}
}

@HttpDslMarker
class ParamContext {
    private val map: MutableMap<String, Any> = mutableMapOf()

    infix fun String.to(v: Any) {
        map[this] = v
    }

    internal fun forEach(action: (k: String, v: Any) -> Unit) = map.forEach(action)
}
