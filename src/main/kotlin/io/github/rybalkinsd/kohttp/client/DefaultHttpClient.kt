package io.github.rybalkinsd.kohttp.client

import io.github.rybalkinsd.kohttp.configuration.config
import okhttp3.OkHttpClient


/**
 * Created by Sergey on 21/07/2018.
 */
val DefaultHttpClient: OkHttpClient = run {
    config.client.let {
        client {
            connectionPool = it.connectionPoolConfig.instance()
            connectTimeout = it.connectTimeout
            readTimeout = it.readTimeout
            writeTimeout = it.writeTimeout
            followRedirects = it.followRedirects
            followSslRedirects = it.followSslRedirects
        }
    }
}
