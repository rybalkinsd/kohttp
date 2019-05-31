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
        path?.let { encodedPath(it) }
        paramContext.forEach { k, v ->
            when (v) {
                is List<*> -> v.forEach { addQueryParameter(k, it.toString()) }
                else -> addQueryParameter(k, v.toString())
            }
        }
    }

    open fun makeBody(): RequestBody = throw UnsupportedOperationException("Request body is not supported for [$method] Method.")

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
