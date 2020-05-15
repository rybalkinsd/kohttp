package io.github.rybalkinsd.kohttp.dsl

import io.github.rybalkinsd.kohttp.client.defaultHttpClient
import io.github.rybalkinsd.kohttp.dsl.context.*
import io.github.rybalkinsd.kohttp.util.translateHttpContext
import okhttp3.Call
import okhttp3.Response

fun <T : HttpContext> http(client: Call.Factory = defaultHttpClient, init: T.() -> Unit): Response {
    return client.newCall(translateHttpContext(null, init).makeRequest()).execute()
}

fun <T : HttpContext> http(client: Call.Factory = defaultHttpClient, method: Method, init: T.() -> Unit): Response {
    return client.newCall(translateHttpContext(method, init).makeRequest()).execute()
}
