package io.github.rybalkinsd.kohttp.dsl.async

import io.github.rybalkinsd.kohttp.client.defaultHttpClient
import io.github.rybalkinsd.kohttp.dsl.context.HttpGetContext
import io.github.rybalkinsd.kohttp.ext.suspendCall
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers.Unconfined
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import okhttp3.Call
import okhttp3.Response


fun asyncHttpGet(client: Call.Factory = defaultHttpClient, init: HttpGetContext.() -> Unit): Deferred<Response> =
    GlobalScope.async(context = Unconfined) {
        client.suspendCall(HttpGetContext().apply(init).makeRequest())
    }
