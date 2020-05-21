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
 * Method provides an asynchronous DSL call of HTTP POST
 *
 * @return a deferred `Response` instance.
 *
 * Usage example with default `defaultHttpClient`:
 *
 *  <pre>
 *  val response: Deferred<Response> = httpPostAsync {
 *      url("https://yourhost:port/path/to/resource")
 *      header { ... }
 *      ...
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
 *  val response: Deferred<Response> = httpPostAsync(customHttpClient) {
 *      ...
 *  }
 * </pre>
 *
 * @see Response
 * @see HttpPostContext
 *
 * @since 0.10.0
 * @author evgeny
 */
fun httpPostAsync(
    client: Call.Factory = defaultHttpClient,
    init: HttpPostContext.() -> Unit
): Deferred<Response> =
    GlobalScope.async(context = Dispatchers.Unconfined) {
        client.suspendCall(HttpPostContext().apply(init).makeRequest())
    }
