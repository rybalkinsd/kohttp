package io.github.rybalkinsd.kohttp.dsl.context

import io.github.rybalkinsd.kohttp.dsl.context.Method.DELETE
import io.github.rybalkinsd.kohttp.dsl.context.Method.GET
import io.github.rybalkinsd.kohttp.dsl.context.Method.HEAD
import io.github.rybalkinsd.kohttp.dsl.context.Method.PATCH
import io.github.rybalkinsd.kohttp.dsl.context.Method.POST
import io.github.rybalkinsd.kohttp.dsl.context.Method.PUT
import okhttp3.Headers
import okhttp3.HttpUrl
import okhttp3.Request
import okhttp3.RequestBody

/**
 * @since 0.1.0
 * @author sergey
 */
@HttpDslMarker
sealed class HttpContext(private val method: Method = GET) : IHttpContext {
    private val paramContext = ParamContext()
    private val headerContext = HeaderContext()

    var scheme: String = "http"
    var host: String = ""
    var port: Int? = null
    var path: String? = null

    override fun param(init: ParamContext.() -> Unit) {
        paramContext.init()
    }

    override fun header(init: HeaderContext.() -> Unit) {
        headerContext.init()
    }

    override fun makeRequest(): Request = with(Request.Builder()) {
        url(makeUrl().build())
        headers(makeHeaders().build())

        when (method) {
            POST, PUT, PATCH, DELETE -> method(method.name, makeBody())
            HEAD -> head()
            GET -> get()
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
        if (!path.isNullOrEmpty()) { encodedPath(path) }
        paramContext.forEach { k, v ->
            when (v) {
                null -> addQueryParameter(k, null)
                is List<*> -> v.forEach { addQueryParameter(k, it.toString()) }
                else -> addQueryParameter(k, v.toString())
            }
        }
    }

    open fun makeBody(): RequestBody = throw UnsupportedOperationException("Request body is not supported for [$method] Method.")

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HttpContext

        if (method != other.method) return false
        if (paramContext != other.paramContext) return false
        if (headerContext != other.headerContext) return false
        if (scheme != other.scheme) return false
        if (host != other.host) return false
        if (port != other.port) return false
        if (path != other.path) return false

        return true
    }

    override fun hashCode(): Int {
        var result = method.hashCode()
        result = 31 * result + paramContext.hashCode()
        result = 31 * result + headerContext.hashCode()
        result = 31 * result + scheme.hashCode()
        result = 31 * result + host.hashCode()
        result = 31 * result + (port ?: 0)
        result = 31 * result + (path?.hashCode() ?: 0)
        return result
    }

}

open class HttpPostContext(method: Method = POST) : HttpContext(method) {
    private var body: RequestBody = RequestBody.create(null, byteArrayOf())

    fun body(contentType: String? = null, init: BodyContext.() -> RequestBody) {
        body = BodyContext(contentType).init()
    }

    fun multipartBody(contentType: String? = null, init: MultipartBodyContext.() -> Unit) {
        body = MultipartBodyContext(contentType).apply { init() }.build()
    }

    override fun makeBody(): RequestBody = body
}

class HttpGetContext : HttpContext()
class HttpHeadContext : HttpContext(method = HEAD)
class HttpPutContext : HttpPostContext(method = PUT)
class HttpPatchContext : HttpPostContext(method = PATCH)
class HttpDeleteContext : HttpPostContext(method = DELETE)

enum class Method {
    GET, POST, PUT, DELETE, PATCH, HEAD
}


internal interface IHttpContext {
    fun param(init: ParamContext.() -> Unit)
    fun header(init: HeaderContext.() -> Unit)
    fun makeRequest(): Request
}


@DslMarker
annotation class HttpDslMarker
