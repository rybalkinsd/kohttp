package com.kohttp.client

import com.kohttp.configuration.Config
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit


/**
 * Created by Sergey on 21/07/2018.
 */
internal val CommonHttpClient: OkHttpClient = Config.instance.client.builder().build()

internal fun Config.Client.builder() = OkHttpClient.Builder().apply {
    with(connectionPool) {
        connectionPool(ConnectionPool(maxIdleConnections, keepAliveDuration, TimeUnit.MILLISECONDS))
    }

    connectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
    readTimeout(readTimeout, TimeUnit.MILLISECONDS)
    writeTimeout(writeTimeout, TimeUnit.MILLISECONDS)
    followRedirects(followRedirects)
    followSslRedirects(followSslRedirects)
}
