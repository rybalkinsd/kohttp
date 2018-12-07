package io.github.rybalkinsd.kohttp.client

import okhttp3.OkHttpClient


fun OkHttpClient.fork(block: ForkClientBuilder.() -> Unit) : OkHttpClient =
    ClientBuilderImpl(this).apply(block).build()
