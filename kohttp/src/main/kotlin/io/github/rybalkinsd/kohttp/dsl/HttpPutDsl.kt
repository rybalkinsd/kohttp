package io.github.rybalkinsd.kohttp.dsl

import io.github.rybalkinsd.kohttp.client.defaultHttpClient
import io.github.rybalkinsd.kohttp.dsl.context.HttpPutContext
import okhttp3.Call
import okhttp3.Response


/**
 * Method provides an synchronous DSL call of HTTP PUT
 *
 * @return a `Response` instance.
 *
 * Usage example with default `defaultHttpClient`:
 *
 *  <pre>
 *  val response: Response = httpPut {
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
 *  val response: Response? = httpPut(customHttpClient) {
 *      ...
 *  }
 * </pre>
 *
 * @see Response
 * @see HttpPutContext
 *
 * @since 0.2.0
 * @author sergey
 */
fun httpPut(client: Call.Factory = defaultHttpClient, init: HttpPutContext.() -> Unit): Response {
    val context = HttpPutContext().apply(init)
    return client.newCall(context.makeRequest()).execute()
}
