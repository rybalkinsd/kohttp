package io.github.rybalkinsd.kohttp.dsl

import io.github.rybalkinsd.kohttp.client.defaultHttpClient
import io.github.rybalkinsd.kohttp.dsl.context.UploadContext
import okhttp3.Call
import okhttp3.Response

/**
 * Method provides a synchronous DSL call of HTTP POST to UPLOAD file
 *
 * @return a `Response` instance.
 *
 * Usage example with default `defaultHttpClient`:
 *
 *  <pre>
 *  val response: Response = upload {
 *      url("http://postman-echo.com/post")
 *      val fileUri = this.javaClass.getResource("/cat.gif").toURI()
 *      file(fileUri)
 *  }
 *  response.use { ... }
 *  </pre>
 *
 *  @param client allow to use your own implementation of HttpClient.
 * `defaultHttpClient` is used by default.
 *
 * <pre>
 *  val response: Response = upload(customHttpClient) {
 *      ...
 *  }
 * </pre>
 *
 * @see Response
 * @see HttpDeleteContext
 *
 * @since 0.8.0
 * @author sergey
 */
fun upload(client: Call.Factory = defaultHttpClient, init: UploadContext.() -> Unit): Response {
    val context = UploadContext().apply(init)
    return client.newCall(context.makeRequest()).execute()
}
