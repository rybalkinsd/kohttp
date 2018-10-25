package com.kohttp.dsl

import com.kohttp.client.CommonHttpClient
import com.kohttp.util.Form
import com.kohttp.util.Json
import okhttp3.Call
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.Response


/**
 * Method provides an synchronous DSL call of HTTP POST
 *
 * @return a `Response` instance.
 *
 * Usage example using the default `CommonHttpClient`:
 *
 *  <pre>
 *  val response: Response? = httpPost {
 *      host = "yourhost"
 *      scheme = "https"
 *      port = 8080
 *      path = "path/to/resource"
 *      param {
 *          "your param" to "value"
 *      }
 *      header { ... }
 *      body { ... }
 *  }
 *  response.use { ... }
 *  </pre>
 *
 *  @param client allow to use your own implementation of HttpClient.
 * `CommonHttpClient` is used by default.
 *
 * <pre>
 *  val response: Response? = httpPost(customHttpClient) {
 *      ...
 *  }
 * </pre>
 *
 * @see Response
 * @see HttpContext
 * @see ParamContext
 * @see HeaderContext
 * @see BodyContext
 *
 * Created by Sergey on 23/07/2018.
 */
fun httpPost(client: Call.Factory = CommonHttpClient, init: HttpPostContext.() -> Unit): Response {
    val context = HttpPostContext().apply(init)
    return client.newCall(context.makeRequest()).execute()
}

open class HttpPostContext(method: Method = Method.POST): HttpContext(method) {
    var body: RequestBody = RequestBody.create(null, byteArrayOf(0))
    fun body(init: BodyContext.() -> RequestBody) {
        body = BodyContext().init()
    }

    override fun makeBody(): RequestBody = body
}

class BodyContext {
    fun json(init: Json.() -> Unit): RequestBody =
        RequestBody.create(MediaType.get("application/json"), Json().also(init).toString())

    fun form(init: Form.() -> Unit): RequestBody =
        RequestBody.create(MediaType.get("application/x-www-form-urlencoded"), Form().also(init).toString())
}
