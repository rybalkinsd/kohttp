package io.github.rybalkinsd.kohttp.client

import okhttp3.OkHttpClient

/**
 * Created by Sergey Rybalkin on 26/11/2018.
 */
fun OkHttpClient.fork(block: OkHttpClient.Builder.() -> Unit) : OkHttpClient {
    return newBuilder().apply(block).build()
}
