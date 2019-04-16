package io.github.rybalkinsd.kohttp.dsl.async

import io.github.rybalkinsd.kohttp.client.defaultHttpClient
import io.github.rybalkinsd.kohttp.dsl.context.HttpPostContext
import io.github.rybalkinsd.kohttp.ext.suspendCall
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers.Unconfined
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import okhttp3.Call
import okhttp3.Response


/**
 *
 * @since 0.8.0
 * @author sergey
 */
fun asyncHttpPost(client: Call.Factory = defaultHttpClient, init: HttpPostContext.() -> Unit): Deferred<Response> =
    GlobalScope.async(context = Unconfined) {
        client.suspendCall(HttpPostContext().apply(init).makeRequest())
    }
