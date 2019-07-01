package io.github.rybalkinsd.kohttp.dsl.async

import io.github.rybalkinsd.kohttp.client.defaultHttpClient
import io.github.rybalkinsd.kohttp.dsl.context.UploadContext
import io.github.rybalkinsd.kohttp.ext.suspendCall
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import okhttp3.Call
import okhttp3.Response

/**
 * Method provides an asynchronous DSL call of HTTP POST to UPLOAD file
 *
 * @return a deferred `Response` instance.
 *
 * Usage example with default `defaultHttpClient`:
 *
 *  <pre>
 *  val response: Deferred<Response> = uploadAsync {
 *      url("http://postman-echo.com/post")
 *      val fileUri = this.javaClass.getResource("/cat.gif").toURI()
 *      file(fileUri)
 *  }
 *  response.await().use { ... }
 *  </pre>
 *
 *  @param client allow to use your own implementation of HttpClient.
 * `defaultHttpClient` is used by default.
 *
 * <pre>
 *  val response: Deferred<Response> = uploadAsync(customHttpClient) {
 *      ...
 *  }
 * </pre>
 *
 * @see Response
 * @see UploadContext
 *
 * @since 0.10.0
 * @author evgeny
 */
fun uploadAsync(
    client: Call.Factory = defaultHttpClient,
    init: UploadContext.() -> Unit
): Deferred<Response> =
    GlobalScope.async(context = Dispatchers.Unconfined) {
        client.suspendCall(UploadContext().apply(init).makeRequest())
    }
