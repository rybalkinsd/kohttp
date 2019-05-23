package io.github.rybalkinsd.kohttp.dsl.async

import io.github.rybalkinsd.kohttp.client.defaultHttpClient
import io.github.rybalkinsd.kohttp.dsl.context.HttpPostContext
import io.github.rybalkinsd.kohttp.ext.suspendCall
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import okhttp3.Call
import okhttp3.Response


/**
 * Method provides an async DSL call of HTTP POST
 *
 * @return a `Response` instance.
 *
 * Usage example using the default `defaultHttpClient`:
 *
 *  <pre>
 *  val response: Response = httpPostAsync {
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
 *  response.await().use {
 *      your code here
 *  }
 *  </pre>
 *
 *  @param client allow to use your own implementation of HttpClient.
 * `defaultHttpClient` is used by default.
 *
 * <pre>
 *  val response: Response? = httpPostAsync(customHttpClient) {
 *      ...
 *  }
 * </pre>
 *
 * @see Response
 * @see HttpPostContext
 *
 * @since 0.2.0
 * @author sergey
 */
fun httpPostAsync(
        client: Call.Factory = defaultHttpClient,
        init: HttpPostContext.() -> Unit
): Deferred<Response> =
        GlobalScope.async(context = Dispatchers.IO) {
            client.suspendCall(HttpPostContext().apply(init).makeRequest())
        }