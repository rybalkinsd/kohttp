package io.github.rybalkinsd.kohttp.dsl.async

import io.github.rybalkinsd.kohttp.client.defaultHttpClient
import io.github.rybalkinsd.kohttp.dsl.context.HttpGetContext
import io.github.rybalkinsd.kohttp.ext.suspendCall
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import okhttp3.Call
import okhttp3.Response


/**
 * Method provides an asynchronous DSL call of HTTP GET
 *
 * @return a deferred `Response` instance.
 *
 * Usage example with default `defaultHttpClient`:
 *
 *  <pre>
 *  val response: Deferred<Response> = httpGetAsync {
 *      url("https://yourhost:port/path/to/resource")
 *      header { ... }
 *      ...
 *  }
 *  response.await().use { ... }
 *  </pre>
 *
 *  @param client allow to use your own implementation of HttpClient.
 * `defaultHttpClient` is used by default.
 *
 * <pre>
 *  val response: Deferred<Response> = httpGetAsync(customHttpClient) {
 *      ...
 *  }
 * </pre>
 *
 * @see Response
 * @see HttpGetContext
 *
 * @since 0.4.0
 * @author sergey
 */
fun httpGetAsync(
    client: Call.Factory = defaultHttpClient,
    init: HttpGetContext.() -> Unit
): Deferred<Response> =
    GlobalScope.async(context = Dispatchers.Unconfined) {
        client.suspendCall(HttpGetContext().apply(init).makeRequest())
    }
