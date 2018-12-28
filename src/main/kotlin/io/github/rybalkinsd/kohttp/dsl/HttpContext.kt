package io.github.rybalkinsd.kohttp.dsl

import io.github.rybalkinsd.kohttp.dsl.Method.*
import io.github.rybalkinsd.kohttp.util.Form
import io.github.rybalkinsd.kohttp.util.Json
import okhttp3.*
import java.io.File

/**
 * Other methods are not supported at the moment
 */
enum class Method {
    GET, POST, PUT, DELETE, PATCH, HEAD
}

internal interface IHttpContext {
    fun makeRequest(): Request
}

private val JSON = MediaType.get("application/json")
private val FORM = MediaType.get("application/x-www-form-urlencoded")

@DslMarker
annotation class HttpDslMarker

@HttpDslMarker
sealed class HttpContext(private val method: Method = GET) : IHttpContext {
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
            when (v) {
                is List<*> -> v.forEach { addQueryParameter(k, it.toString()) }
                else -> addQueryParameter(k, v.toString())
            }
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

class HttpGetContext : HttpContext()
class HttpHeadContext : HttpContext(method = Method.HEAD)
class HttpPutContext: HttpPostContext(method = Method.PUT)
class HttpPatchContext: HttpPostContext(method = Method.PATCH)

open class HttpPostContext(method: Method = Method.POST): HttpContext(method) {
    private var body: RequestBody = RequestBody.create(null, byteArrayOf())

    fun body(contentType: String? = null, init: BodyContext.() -> RequestBody) {
        body = BodyContext(contentType).init()
    }
    override fun makeBody(): RequestBody = body

}

class BodyContext(type: String?) {

    private val mediaType = type?.let { MediaType.get(it) }

    fun string(content: String): RequestBody {
        return RequestBody.create(mediaType, content)
    }

    fun file(content: File): RequestBody {
        return RequestBody.create(mediaType, content)
    }

    fun file(path: String): RequestBody {
        return RequestBody.create(mediaType, File(path))
    }

    fun bytes(content: ByteArray): RequestBody {
        return RequestBody.create(mediaType, content)
    }

    private fun MediaType.create(contentProducer: () -> Any): RequestBody {
        return when (val content = contentProducer()) {
            is String -> RequestBody.create(this, content)
            is File -> RequestBody.create(this, content)
            is ByteArray -> RequestBody.create(this, content)
            else -> throw IllegalArgumentException("${content.javaClass.name} is not allowed as body content")
        }
    }

    fun json(init: Json.() -> Unit): RequestBody =
        JSON.create { Json().also(init).toString() }

    fun form(init: Form.() -> Unit): RequestBody =
        FORM.create { Form().also(init).toString() }

    fun json(content: String): RequestBody =
        JSON.create { content }

    fun form(content: String): RequestBody =
        FORM.create { content }
}
