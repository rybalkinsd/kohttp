package com.kohttp.dsl

import com.kohttp.client.CommonHttpClient
import okhttp3.Call
import okhttp3.Response

/**
 *
 * @param client gives a possibility to provide your implementation of HttpClient
 * `CommonHttpClient` by default
 *
 * Created by Sergey on 22/07/2018.
 */
fun httpHead(client: Call.Factory = CommonHttpClient, init: HttpHeadContext.() -> Unit): Response {
    val context = HttpHeadContext().apply(init)
    return client.newCall(context.makeRequest()).execute()
}

class HttpHeadContext : HttpContext(method = Method.HEAD)
