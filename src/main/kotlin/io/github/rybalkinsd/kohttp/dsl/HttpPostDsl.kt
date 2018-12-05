package io.github.rybalkinsd.kohttp.dsl

import io.github.rybalkinsd.kohttp.client.DefaultHttpClient
import okhttp3.Call
import okhttp3.Response


/**
 * Method provides an synchronous DSL call of HTTP POST
 *
 * @return a `Response` instance.
 *
 * Usage example using the default `DefaultHttpClient`:
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
 * `DefaultHttpClient` is used by default.
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
fun httpPost(client: Call.Factory = DefaultHttpClient, init: HttpPostContext.() -> Unit): Response {
    val context = HttpPostContext().apply(init)
    return client.newCall(context.makeRequest()).execute()
}
