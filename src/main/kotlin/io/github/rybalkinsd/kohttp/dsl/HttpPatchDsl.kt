package io.github.rybalkinsd.kohttp.dsl

import io.github.rybalkinsd.kohttp.client.defaultHttpClient
import okhttp3.Call
import okhttp3.Response


/**
 * Method provides an synchronous DSL call of HTTP PATCH
 *
 * @return a `Response` instance.
 *
 * Usage example using the default `defaultHttpClient`:
 *
 *  <pre>
 *  val response: Response = httpPatch {
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
 *  val response: Response? = httpPatch(customHttpClient) {
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
 * Created by Bpaxio on 06/09/2018.
 */
fun httpPatch(client: Call.Factory = defaultHttpClient, init: HttpPatchContext.() -> Unit): Response {
    val context = HttpPatchContext().apply(init)
    return client.newCall(context.makeRequest()).execute()
}
