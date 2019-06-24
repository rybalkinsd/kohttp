package io.github.rybalkinsd.kohttp.dsl

import io.github.rybalkinsd.kohttp.client.defaultHttpClient
import io.github.rybalkinsd.kohttp.dsl.context.HttpHeadContext
import okhttp3.Call
import okhttp3.Response

/**
 * Method provides an synchronous DSL call of HTTP HEAD
 *
 * @return a `Response` instance.
 *
 * Usage example with default `defaultHttpClient`:
 *
 *  <pre>
 *  val response: Response = httpHead {
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
 *  val response: Response? = httpHead(customHttpClient) {
 *      ...
 *  }
 * </pre>
 *
 * @see Response
 * @see HttpHeadContext
 *
 * @since 0.1.0
 * @author sergey
 */
fun httpHead(client: Call.Factory = defaultHttpClient, init: HttpHeadContext.() -> Unit): Response {
    val context = HttpHeadContext().apply(init)
    return client.newCall(context.makeRequest()).execute()
}
