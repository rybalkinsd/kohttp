package io.github.rybalkinsd.kohttp.client

import io.github.rybalkinsd.kohttp.configuration.ConnectionPoolConfig
import io.github.rybalkinsd.kohttp.configuration.config
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit


/**
 * A default http client.
 * Default client is recommended to use for your requests.
 *
 * Default client is either configured with `/kohttp.yaml`
 * or by defaults
 *  connectTimeout: 10 seconds
 *  readTimeout: 10 seconds
 *  writeTimeout: 10 seconds
 *  followRedirects: true
 *  followSslRedirects: true
 *
 *  maxIdleConnections:  5
 *  keepAliveDuration: 5 minutes
 *
 *
 * For specific needs it's possible to fork `defaultHttpClient`
 * @see `io.github.rybalkinsd.kohttp.client.OkHttpClientExtKt.fork`
 *
 * @author sergey 21/07/2018
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
