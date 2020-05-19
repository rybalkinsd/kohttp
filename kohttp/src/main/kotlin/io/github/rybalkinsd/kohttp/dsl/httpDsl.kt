package io.github.rybalkinsd.kohttp.dsl

import io.github.rybalkinsd.kohttp.client.defaultHttpClient
import io.github.rybalkinsd.kohttp.dsl.context.HttpContext
import io.github.rybalkinsd.kohttp.dsl.context.Method
import io.github.rybalkinsd.kohttp.util.makeHttpContext
import okhttp3.Call
import okhttp3.Response

fun http(client: Call.Factory = defaultHttpClient, method: Method, init: HttpContext.() -> Unit): Response {
    return client.newCall(method.makeHttpContext().apply(init).makeRequest()).execute()
}