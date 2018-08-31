package com.kohttp.dsl

import com.kohttp.client.CommonHttpClient
import com.kohttp.util.Form
import com.kohttp.util.Json
import okhttp3.Call
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.Response


/**
 * @param client gives a possibility to provide your implementation of HttpClient
 * `CommonHttpClient` by default
 *
 * Created by Sergey on 23/07/2018.
 */
fun httpPost(client: Call.Factory = CommonHttpClient, init: HttpPostContext.() -> Unit): Response {
    val context = HttpPostContext().apply(init)
    return client.newCall(context.makeRequest()).execute()
}

open class HttpPostContext(method: Method = Method.POST): HttpContext(method) {
    lateinit var body: RequestBody
    fun body(init: BodyContext.() -> RequestBody) {
        body = BodyContext().init()
    }

    override fun makeBody(): RequestBody = body
}

class BodyContext {
    fun json(init: Json.() -> Unit): RequestBody =
        RequestBody.create(MediaType.get("application/json"), Json().also(init).toString())

    fun form(init: Form.() -> Unit): RequestBody =
        RequestBody.create(MediaType.get("application/x-www-form-urlencoded"), Form().also(init).toString())
}
