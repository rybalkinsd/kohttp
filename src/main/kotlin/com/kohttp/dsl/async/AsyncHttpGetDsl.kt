package com.kohttp.dsl.async

import com.kohttp.client.CommonHttpClient
import com.kohttp.dsl.HttpGetContext
import com.kohttp.ext.suspendCall
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.Unconfined
import kotlinx.coroutines.experimental.async
import okhttp3.Call
import okhttp3.Response


fun asyncHttpGet(client: Call.Factory = CommonHttpClient, init: HttpGetContext.() -> Unit): Deferred<Response> =
    async(context = Unconfined) {
        client.suspendCall(HttpGetContext().apply(init).makeRequest())
    }
