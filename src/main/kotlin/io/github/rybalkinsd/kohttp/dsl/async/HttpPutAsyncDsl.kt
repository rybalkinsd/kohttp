package io.github.rybalkinsd.kohttp.dsl.async

import io.github.rybalkinsd.kohttp.client.defaultHttpClient
import io.github.rybalkinsd.kohttp.dsl.context.HttpGetContext
import io.github.rybalkinsd.kohttp.dsl.context.HttpPutContext
import io.github.rybalkinsd.kohttp.ext.suspendCall
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import okhttp3.Call
import okhttp3.Response


/**
 * Method provides an asynchronous DSL call of HTTP PUT
 *
 * @return a deferred `Response` instance.
 *
 * Usage example using the default `defaultHttpClient`:
 *
 *  <pre>
 *  val response: Deferred<Response> = httpPutAsync {
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
 *  response.await().use { ... }
 *  </pre>
 *
 *  @param client allow to use your own implementation of HttpClient.
 * `defaultHttpClient` is used by default.
 *
 * <pre>
 *  val response: Deferred<Response> = httpPutAsync(customHttpClient) {
 *      ...
 *  }
 * </pre>
 *
 * @see Response
 * @see HttpPutContext
 *
 * @since 0.10.0
 * @author evgeny
 */
fun httpPutAsync(
        client: Call.Factory = defaultHttpClient,
        init: HttpPutContext.() -> Unit
): Deferred<Response> =
        GlobalScope.async(context = Dispatchers.Unconfined) {
            client.suspendCall(HttpPutContext().apply(init).makeRequest())
        }
