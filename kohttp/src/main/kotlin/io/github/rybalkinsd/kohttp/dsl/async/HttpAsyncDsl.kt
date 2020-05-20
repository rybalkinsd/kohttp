package io.github.rybalkinsd.kohttp.dsl.async

import io.github.rybalkinsd.kohttp.client.defaultHttpClient
import io.github.rybalkinsd.kohttp.dsl.context.*
import io.github.rybalkinsd.kohttp.ext.suspendCall
import io.github.rybalkinsd.kohttp.util.makeHttpContext
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import okhttp3.Call
import okhttp3.Response

/**
 * Method provides an asynchronous DSL call of a HTTP request
 *
 * @return a deferred `Response` instance.
 *
 * Usage example with default `defaultHttpClient`:
 *
 *  <pre>
 *  val response: Deferred<Response> = httpAsync(method = Method.GET) {
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
 *  val response: Deferred<Response> = httpAsync(customHttpClient, Method.GET) {
 *      ...
 *  }
 * </pre>
 *
 * @see Response
 * @see HttpContext
 * @see Method
 *
 * @since 0.11.2
 * @author hakky54
 */
fun httpAsync(
        client: Call.Factory = defaultHttpClient,
        method: Method,
        init: HttpContext.() -> Unit
): Deferred<Response> =
        GlobalScope.async(context = Dispatchers.Unconfined) {
            client.suspendCall(method.makeHttpContext().apply(init).makeRequest())
        }