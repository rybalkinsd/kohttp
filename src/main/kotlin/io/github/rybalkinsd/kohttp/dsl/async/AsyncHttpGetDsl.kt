package io.github.rybalkinsd.kohttp.dsl.async

import io.github.rybalkinsd.kohttp.client.defaultHttpClient
import io.github.rybalkinsd.kohttp.dsl.context.HttpGetContext
import kotlinx.coroutines.Deferred
import okhttp3.Call
import okhttp3.Response


/**
 *
 * @since 0.4.0
 * @author sergey
 */
@Suppress("DeferredIsResult")
@Deprecated(
    message = "Use httpGetAsync instead. This function was renamed according to Kotlin Style Guide." +
        "This function will be removed in version 0.12.0",
    replaceWith = ReplaceWith(
        "httpGetAsync(client, init)",
        "io.github.rybalkinsd.kohttp.dsl.async.httpGetAsync")
)
fun asyncHttpGet(client: Call.Factory = defaultHttpClient, init: HttpGetContext.() -> Unit): Deferred<Response> =
    httpGetAsync(client, init)
