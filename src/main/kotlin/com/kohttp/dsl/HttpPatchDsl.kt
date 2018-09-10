package com.kohttp.dsl

import com.kohttp.client.CommonHttpClient
import okhttp3.Call
import okhttp3.Response


/**
 * @param client gives a possibility to provide your implementation of HttpClient
 * `CommonHttpClient` by default.
 *
 * @return a `Response` instance.
 *
 * Usage example using the default `CommonHttpClient`:
 *
 *  <pre>
 *  val response: Response? = httpPatch {
 *      host = "yourhost"
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
 * Also you can use your own implementation of HttpClient adding param in usage:
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
 * Created by Bpaxio on 06/09/2018.
 */
fun httpPatch(client: Call.Factory = CommonHttpClient, init: HttpPatchContext.() -> Unit): Response {
    val context = HttpPatchContext().apply(init)
    return client.newCall(context.makeRequest()).execute()
}

class HttpPatchContext: HttpPostContext(method = Method.PATCH)
