package io.github.rybalkinsd.kohttp.dsl.async

import io.github.rybalkinsd.kohttp.client.defaultHttpClient
import io.github.rybalkinsd.kohttp.dsl.context.HttpGetContext
import io.github.rybalkinsd.kohttp.ext.suspendCall
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import okhttp3.Call
import okhttp3.Response


/**
 *
 * @since 0.4.0
 * @author sergey
 */
fun httpGetAsync(
        client: Call.Factory = defaultHttpClient,
        init: HttpGetContext.() -> Unit
): Deferred<Response> =
        GlobalScope.async(context = Dispatchers.IO) {
            client.suspendCall(HttpGetContext().apply(init).makeRequest())
        }
