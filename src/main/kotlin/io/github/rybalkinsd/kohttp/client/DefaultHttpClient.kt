package io.github.rybalkinsd.kohttp.client

import io.github.rybalkinsd.kohttp.configuration.ConnectionPoolConfig
import io.github.rybalkinsd.kohttp.configuration.config
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit


/**
 * Created by Sergey on 21/07/2018.
 */
val defaultHttpClient: OkHttpClient = config.client.let {
    client {
        connectionPool = it.connectionPoolConfig.create()
        connectTimeout = it.connectTimeout
        readTimeout = it.readTimeout
        writeTimeout = it.writeTimeout
        followRedirects = it.followRedirects
        followSslRedirects = it.followSslRedirects
    }
}

private fun ConnectionPoolConfig.create() = ConnectionPool(maxIdleConnections, keepAliveDuration, TimeUnit.MILLISECONDS)
