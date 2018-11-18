package io.github.rybalkinsd.kohttp.dsl

import io.github.rybalkinsd.kohttp.client.CommonHttpClient
import io.github.rybalkinsd.kohttp.dsl.Method.DELETE
import okhttp3.Call
import okhttp3.Response


/**
 * Method provides an synchronous DSL call of HTTP DELETE
 *
 * @return a `Response` instance.
 *
 * Usage example using the default `CommonHttpClient`:
 *
 *  <pre>
 *  val response: Response? = httpDelete {
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
 *  val response: Response? = httpDelete(customHttpClient) {
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
fun httpDelete(client: Call.Factory = CommonHttpClient, init: HttpDeleteContext.() -> Unit): Response {
    val context = HttpDeleteContext().apply(init)
    return client.newCall(context.makeRequest()).execute()
}

class HttpDeleteContext: HttpPostContext(method = DELETE)
