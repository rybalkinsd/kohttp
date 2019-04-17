package io.github.rybalkinsd.kohttp.dsl

import io.github.rybalkinsd.kohttp.client.defaultHttpClient
import io.github.rybalkinsd.kohttp.dsl.context.UploadContext
import okhttp3.Call
import okhttp3.Response

/**
 *
 * @since 0.8.0
 * @author sergey
 */
fun upload(client: Call.Factory = defaultHttpClient, init: UploadContext.() -> Unit): Response {
    val context = UploadContext().apply(init)
    return client.newCall(context.makeRequest()).execute()
}
