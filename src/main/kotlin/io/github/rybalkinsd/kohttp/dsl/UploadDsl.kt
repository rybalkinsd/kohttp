package io.github.rybalkinsd.kohttp.dsl

import io.github.rybalkinsd.kohttp.client.defaultHttpClient
import io.github.rybalkinsd.kohttp.dsl.context.UploadContext
import okhttp3.Call


fun upload(client: Call.Factory = defaultHttpClient, init: UploadContext.() -> Unit) {
    client.newCall(UploadContext().apply(init).makeRequest()).execute()
}

