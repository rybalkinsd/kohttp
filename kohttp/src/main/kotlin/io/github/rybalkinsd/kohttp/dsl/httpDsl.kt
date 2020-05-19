package io.github.rybalkinsd.kohttp.dsl

import io.github.rybalkinsd.kohttp.client.defaultHttpClient
import io.github.rybalkinsd.kohttp.dsl.context.HttpContext
import io.github.rybalkinsd.kohttp.dsl.context.Method
import io.github.rybalkinsd.kohttp.util.createHttpContext
import okhttp3.Call
import okhttp3.Response

fun <T : HttpContext> http(client: Call.Factory = defaultHttpClient, method: Method, init: T.() -> Unit): Response {
    return client.newCall(method.createHttpContext<T>().apply(init).makeRequest()).execute()
}