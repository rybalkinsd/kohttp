package io.github.rybalkinsd.kohttp.dsl.async

import io.github.rybalkinsd.kohttp.client.defaultHttpClient
import io.github.rybalkinsd.kohttp.dsl.context.*
import io.github.rybalkinsd.kohttp.ext.suspendCall
import io.github.rybalkinsd.kohttp.util.translateHttpContext
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import okhttp3.Call
import okhttp3.Response

fun <T : HttpContext> httpAsync(
        client: Call.Factory = defaultHttpClient,
        init: T.() -> Unit
): Deferred<Response> =
        GlobalScope.async(context = Dispatchers.Unconfined) {
            client.suspendCall(translateHttpContext(null, init).makeRequest())
        }

fun <T : HttpContext> httpAsync(
        client: Call.Factory = defaultHttpClient,
        method: Method,
        init: T.() -> Unit
): Deferred<Response> =
        GlobalScope.async(context = Dispatchers.Unconfined) {
            client.suspendCall(translateHttpContext(method, init).makeRequest())
        }