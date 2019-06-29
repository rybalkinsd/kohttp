package io.github.rybalkinsd.kohttp.dsl

import io.github.rybalkinsd.kohttp.client.defaultHttpClient
import io.github.rybalkinsd.kohttp.dsl.context.HttpDeleteContext
import okhttp3.Call
import okhttp3.Response


/**
 * Method provides an synchronous DSL call of HTTP DELETE
 *
 * @return a `Response` instance.
 *
 * Usage example with default `defaultHttpClient`:
 *
 *  <pre>
 *  val response: Response = httpDelete {
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
 * `defaultHttpClient` is used by default.
 *
 * <pre>
 *  val response: Response? = httpDelete(customHttpClient) {
 *      ...
 *  }
 * </pre>
 *
 * @see Response
 * @see HttpDeleteContext
 *
 * @since 0.3.2
 * @author sergey
 */
fun httpDelete(client: Call.Factory = defaultHttpClient, init: HttpDeleteContext.() -> Unit): Response {
    val context = HttpDeleteContext().apply(init)
    return client.newCall(context.makeRequest()).execute()
}
