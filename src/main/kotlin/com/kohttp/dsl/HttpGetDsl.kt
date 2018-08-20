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
fun httpGet(client: Call.Factory = CommonHttpClient, init: HttpGetContext.() -> Unit): Response {
    val context = HttpGetContext().apply(init)
    return client.newCall(context.makeRequest()).execute()
}

class HttpGetContext : HttpContext()
