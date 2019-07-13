package io.github.rybalkinsd.kohttp.dsl.async

import io.github.rybalkinsd.kohttp.client.defaultHttpClient
import io.github.rybalkinsd.kohttp.dsl.context.HttpPatchContext
import io.github.rybalkinsd.kohttp.ext.suspendCall
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import okhttp3.Call
import okhttp3.Response


/**
 * Method provides an asynchronous DSL call of HTTP PATCH
 *
 * @return a deferred `Response` instance.
 *
 * Usage example with default `defaultHttpClient`:
 *
 *  <pre>
 *  val response: Deferred<Response> = httpPatchAsync {
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
 *  val response: Deferred<Response> = httpPatchAsync(customHttpClient) {
 *      ...
 *  }
 * </pre>
 *
 * @see Response
 * @see HttpPatchContext
 *
 * @since 0.10.0
 * @author evgeny
 */
fun httpPatchAsync(
    client: Call.Factory = defaultHttpClient,
    init: HttpPatchContext.() -> Unit
): Deferred<Response> =
    GlobalScope.async(context = Dispatchers.Unconfined) {
        client.suspendCall(HttpPatchContext().apply(init).makeRequest())
    }
