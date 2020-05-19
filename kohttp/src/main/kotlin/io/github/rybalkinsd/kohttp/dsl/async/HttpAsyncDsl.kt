package io.github.rybalkinsd.kohttp.dsl.async

import io.github.rybalkinsd.kohttp.client.defaultHttpClient
import io.github.rybalkinsd.kohttp.dsl.context.*
import io.github.rybalkinsd.kohttp.ext.suspendCall
import io.github.rybalkinsd.kohttp.util.makeHttpContext
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import okhttp3.Call
import okhttp3.Response

fun httpAsync(
        client: Call.Factory = defaultHttpClient,
        method: Method,
        init: HttpContext.() -> Unit
): Deferred<Response> =
        GlobalScope.async(context = Dispatchers.Unconfined) {
            client.suspendCall(method.makeHttpContext().apply(init).makeRequest())
        }