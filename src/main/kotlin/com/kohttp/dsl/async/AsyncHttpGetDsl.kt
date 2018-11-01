package com.kohttp.dsl.async

import com.kohttp.client.CommonHttpClient
import com.kohttp.dsl.HttpGetContext
import com.kohttp.ext.suspendCall
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers.Unconfined
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import okhttp3.Call
import okhttp3.Response


fun asyncHttpGet(client: Call.Factory = CommonHttpClient, init: HttpGetContext.() -> Unit): Deferred<Response> =
    GlobalScope.async(context = Unconfined) {
        client.suspendCall(HttpGetContext().apply(init).makeRequest())
    }
