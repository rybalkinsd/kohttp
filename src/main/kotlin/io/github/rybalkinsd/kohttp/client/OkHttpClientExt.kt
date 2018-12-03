package io.github.rybalkinsd.kohttp.client

import okhttp3.OkHttpClient


fun OkHttpClient.fork(block: ForkClientBuilder.() -> Unit) : OkHttpClient {
    return ForkClientBuilder(this).apply(block).build()
}

class ForkClientBuilder(client: OkHttpClient): ClientBuilder(client)

fun client(block: ClientBuilder.() -> Unit) = ClientBuilder().apply(block).build()
