package io.github.rybalkinsd.kohttp.dsl

import io.github.rybalkinsd.kohttp.client.defaultHttpClient
import io.github.rybalkinsd.kohttp.dsl.context.HttpContext
import io.github.rybalkinsd.kohttp.dsl.context.Method
import io.github.rybalkinsd.kohttp.util.makeHttpContext
import okhttp3.Call
import okhttp3.Response

/**
 * Method provides a synchronous DSL call of a HTTP request
 *
 * @return a `Response` instance.
 *
 * Usage example with default `defaultHttpClient`:
 *
 *  <pre>
 *  val response: Response = http(method = Method.GET) {
 *      host = "yourhost"
 *      scheme = "https"
 *      port = 8080
 *      path = "path/to/resource"
 *      param {
 *          "your param" to "value"
 *      }
 *      header { ... }
 *  }
 *  response.use { ... }
 *  </pre>
 *
 *  @param client allow to use your own implementation of HttpClient.
 * `defaultHttpClient` is used by default.
 *
 * <pre>
 *  val response: Response? = http(customHttpClient, Method.GET) {
 *      ...
 *  }
 * </pre>
 *
 * @see Response
 * @see HttpContext
 * @see Method
 *
 * @since 0.11.2
 * @author hakky54
 */
fun http(client: Call.Factory = defaultHttpClient, method: Method, init: HttpContext.() -> Unit): Response {
    return client.newCall(method.makeHttpContext().apply(init).makeRequest()).execute()
}