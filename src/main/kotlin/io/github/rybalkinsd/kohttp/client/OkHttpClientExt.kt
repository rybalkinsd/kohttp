package io.github.rybalkinsd.kohttp.client

import okhttp3.*
import java.net.Proxy
import java.net.ProxySelector
import java.util.concurrent.TimeUnit
import javax.net.SocketFactory
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSocketFactory

/**
 * Created by Sergey Rybalkin on 26/11/2018.
 */
fun OkHttpClient.fork(block: OkHttpClient.Builder.() -> Unit) : OkHttpClient {
    return newBuilder().apply(block).build()
}

/**
 *
 * Migrate getters to private when
 * https://youtrack.jetbrains.com/issue/KT-3110
 * will be implemented
 *
 */
class ClientBuilder {
    private val builder = OkHttpClient.Builder()

    var dispatcher: Dispatcher
        set(value) { builder.dispatcher(value) }
        get() = throw UnsupportedOperationException()

    var proxy: Proxy?
        set(value) { builder.proxy(value) }
        get() = throw UnsupportedOperationException()

    var protocols: List<Protocol>
        set(value) { builder.protocols(value) }
        get() = throw UnsupportedOperationException()

    var connectionSpecs: List<ConnectionSpec>
        set(value) { builder.connectionSpecs(value) }
        get() = throw UnsupportedOperationException()

    var interceptors: List<Interceptor>
        set(value) { value.forEach { builder.addInterceptor(it) } }
        get() = throw UnsupportedOperationException()

    var networkInterceptors: List<Interceptor>
        set(value) { value.forEach { builder.addNetworkInterceptor(it) } }
        get() = throw UnsupportedOperationException()

    var eventListenerFactory: EventListener.Factory
        set(value) { builder.eventListenerFactory(value) }
        get() = throw UnsupportedOperationException()

    var proxySelector: ProxySelector
        set(value) { builder.proxySelector(value) }
        get() = throw UnsupportedOperationException()

    var cookieJar: CookieJar
        set(value) { builder.cookieJar(value) }
        get() = throw UnsupportedOperationException()

    var cache: Cache?
        set(value) { builder.cache(value) }
        get() = throw UnsupportedOperationException()

    var socketFactory: SocketFactory
        set(value) { builder.socketFactory(value) }
        get() = throw UnsupportedOperationException()

    var sslSocketFactory: SSLSocketFactory?
        set(value) { builder.sslSocketFactory(value) }
        get() = throw UnsupportedOperationException()

    var hostnameVerifier: HostnameVerifier
        set(value) { builder.hostnameVerifier(value) }
        get() = throw UnsupportedOperationException()

    var certificatePinner: CertificatePinner
        set(value) { builder.certificatePinner(value) }
        get() = throw UnsupportedOperationException()

    var proxyAuthenticator: Authenticator
        set(value) { builder.proxyAuthenticator(value) }
        get() = throw UnsupportedOperationException()

    var authenticator: Authenticator
        set(value) { builder.authenticator(value) }
        get() = throw UnsupportedOperationException()

    var connectionPool: ConnectionPool
        set(value) { builder.connectionPool(value) }
        get() = throw UnsupportedOperationException()

    var dns: Dns
        set(value) { builder.dns(value) }
        get() = throw UnsupportedOperationException()

    var followSslRedirects: Boolean
        set(value) { builder.followSslRedirects(value) }
        get() = throw UnsupportedOperationException()

    var followRedirects: Boolean
        set(value) { builder.followRedirects(value) }
        get() = throw UnsupportedOperationException()

    var retryOnConnectionFailure: Boolean
        set(value) { builder.retryOnConnectionFailure(value) }
        get() = throw UnsupportedOperationException()

    var connectTimeout: Long
        set(value) { builder.connectTimeout(value, TimeUnit.MILLISECONDS) }
        get() = throw UnsupportedOperationException()

    var readTimeout: Long
        set(value) { builder.readTimeout(value, TimeUnit.MILLISECONDS) }
        get() = throw UnsupportedOperationException()

    var writeTimeout: Long
        set(value) { builder.writeTimeout(value, TimeUnit.MILLISECONDS) }
        get() = throw UnsupportedOperationException()

    var pingInterval: Long
        set(value) { builder.pingInterval(value, TimeUnit.MILLISECONDS) }
        get() = throw UnsupportedOperationException()

    fun build(): OkHttpClient = builder.build()
}

fun client(block: ClientBuilder.() -> Unit) = ClientBuilder().apply(block).build()