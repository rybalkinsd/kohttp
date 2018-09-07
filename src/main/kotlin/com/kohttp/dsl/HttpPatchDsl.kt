package com.kohttp.dsl

import com.kohttp.client.CommonHttpClient
import okhttp3.Call
import okhttp3.Response


/**
 * @param client gives a possibility to provide your implementation of HttpClient
 * `CommonHttpClient` by default
 *
 * Created by Bpaxio on 06/09/2018.
 */
fun httpPatch(client: Call.Factory = CommonHttpClient, init: HttpPatchContext.() -> Unit): Response {
    val context = HttpPatchContext().apply(init)
    return client.newCall(context.makeRequest()).execute()
}

class HttpPatchContext: HttpPostContext(method = Method.PATCH)
