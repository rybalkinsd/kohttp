package com.kohttp.dsl

import com.kohttp.client.CommonHttpClient
import okhttp3.Call
import okhttp3.RequestBody
import okhttp3.Response


/**
 * @param client gives a possibility to provide your implementation of HttpClient
 * `CommonHttpClient` by default
 *
 * Created by Sergey on 23/07/2018.
 */
fun httpPut(client: Call.Factory = CommonHttpClient, init: HttpPutContext.() -> Unit): Response {
    val context = HttpPutContext().apply(init)
    return client.newCall(context.makeRequest()).execute()
}

class HttpPutContext: HttpContext(method = Method.PUT) {
    lateinit var body: RequestBody
    fun body(init: BodyContext.() -> RequestBody) {
        body = BodyContext().init()
    }

    override fun makeBody(): RequestBody = body
}
