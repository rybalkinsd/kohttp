package io.github.rybalkinsd.kohttp.client

import io.github.rybalkinsd.kohttp.configuration.config
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit


/**
 * Created by Sergey on 21/07/2018.
 */
val CommonHttpClient: OkHttpClient = run {
    val c = config.client
    client {
        connectionPool = with(c.connectionPool) {
            ConnectionPool(maxIdleConnections, keepAliveDuration, TimeUnit.MILLISECONDS)
        }
        connectTimeout = c.connectTimeout
        readTimeout = c.readTimeout
        writeTimeout = c.writeTimeout
        followRedirects = c.followRedirects
        followSslRedirects = c.followSslRedirects
    }
}
