package io.github.rybalkinsd.kohttp.client

import okhttp3.OkHttpClient

/**
 *
 * @since 0.5.0
 * @author sergey
 */
fun OkHttpClient.fork(block: ForkClientBuilder.() -> Unit): OkHttpClient =
    ClientBuilderImpl(this).apply(block).build()
