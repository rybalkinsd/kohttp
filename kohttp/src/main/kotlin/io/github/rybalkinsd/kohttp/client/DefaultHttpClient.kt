package io.github.rybalkinsd.kohttp.client

import io.github.rybalkinsd.kohttp.configuration.ConnectionPoolConfig
import io.github.rybalkinsd.kohttp.configuration.DispatcherConfig
import io.github.rybalkinsd.kohttp.configuration.config
import okhttp3.ConnectionPool
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit


/**
 * A default http client.
 * Default client is recommended to use for your requests.
 *
 * Default client is either configured with
 *  connectTimeout: 10 seconds
 *  readTimeout: 10 seconds
 *  writeTimeout: 10 seconds
 *  followRedirects: true
 *  followSslRedirects: true
 *
 *  maxIdleConnections:  5
 *  keepAliveDuration: 5 minutes
 *
 *  maxRequests: 256
 *  maxRequestsPerHost: 256
 *
 * For specific needs it's possible to fork `defaultHttpClient`
 * @see `io.github.rybalkinsd.kohttp.client.OkHttpClientExtKt.fork`
 *
 * @since 0.0.1
 * @author sergey
 */
val defaultHttpClient: OkHttpClient = config.client.let {
    client {
        connectionPool = it.connectionPoolConfig.create()
        connectTimeout = it.connectTimeout
        readTimeout = it.readTimeout
        writeTimeout = it.writeTimeout
        followRedirects = it.followRedirects
        followSslRedirects = it.followSslRedirects
        dispatcher = it.dispatcher.create()
    }
}

private fun DispatcherConfig.create() =
    Dispatcher().apply {
        this.maxRequests = this@create.maxRequests
        this.maxRequestsPerHost = this@create.maxRequestsPerHost
    }

private fun ConnectionPoolConfig.create() =
    ConnectionPool(maxIdleConnections, keepAliveDuration, TimeUnit.MILLISECONDS)
