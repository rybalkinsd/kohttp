package io.github.rybalkinsd.kohttp.client

import okhttp3.Authenticator
import okhttp3.Cache
import okhttp3.CertificatePinner
import okhttp3.ConnectionPool
import okhttp3.ConnectionSpec
import okhttp3.CookieJar
import okhttp3.Dispatcher
import okhttp3.Dns
import okhttp3.EventListener
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import java.net.Proxy
import java.net.ProxySelector
import java.util.concurrent.TimeUnit
import javax.net.SocketFactory
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSocketFactory

/**
 *
 * Migrate getters to private when
 * https://youtrack.jetbrains.com/issue/KT-3110
 * will be implemented
 *
 */
open class ClientBuilder {
    private val builder: OkHttpClient.Builder

    constructor() {
        builder = OkHttpClient.Builder()
    }

    constructor(client: OkHttpClient) {
        builder = client.newBuilder()
    }

    var dispatcher: Dispatcher
        set(value) { builder.dispatcher(value) }
        @Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
        get() = throw UnsupportedOperationException()

    open var proxy: Proxy?
        set(value) { builder.proxy(value) }
        @Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
        get() = throw UnsupportedOperationException()

    var protocols: List<Protocol>
        set(value) { builder.protocols(value) }
        @Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
        get() = throw UnsupportedOperationException()

    var connectionSpecs: List<ConnectionSpec>
        set(value) { builder.connectionSpecs(value) }
        @Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
        get() = throw UnsupportedOperationException()

    open var interceptors: List<Interceptor>
        set(value) { value.forEach { builder.addInterceptor(it) } }
        @Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
        get() = throw UnsupportedOperationException()

    open var networkInterceptors: List<Interceptor>
        set(value) { value.forEach { builder.addNetworkInterceptor(it) } }
        @Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
        get() = throw UnsupportedOperationException()

    var eventListenerFactory: EventListener.Factory
        set(value) { builder.eventListenerFactory(value) }
        @Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
        get() = throw UnsupportedOperationException()

    open var proxySelector: ProxySelector
        set(value) { builder.proxySelector(value) }
        @Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
        get() = throw UnsupportedOperationException()

    open var cookieJar: CookieJar
        set(value) { builder.cookieJar(value) }
        @Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
        get() = throw UnsupportedOperationException()

    open var cache: Cache?
        set(value) { builder.cache(value) }
        @Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
        get() = throw UnsupportedOperationException()

    var socketFactory: SocketFactory
        set(value) { builder.socketFactory(value) }
        @Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
        get() = throw UnsupportedOperationException()

    var sslSocketFactory: SSLSocketFactory
        set(value) { builder.sslSocketFactory(value) }
        @Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
        get() = throw UnsupportedOperationException()

    open var hostnameVerifier: HostnameVerifier
        set(value) { builder.hostnameVerifier(value) }
        @Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
        get() = throw UnsupportedOperationException()

    open var certificatePinner: CertificatePinner
        set(value) { builder.certificatePinner(value) }
        @Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
        get() = throw UnsupportedOperationException()

    open var proxyAuthenticator: Authenticator
        set(value) { builder.proxyAuthenticator(value) }
        @Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
        get() = throw UnsupportedOperationException()

    open var authenticator: Authenticator
        set(value) { builder.authenticator(value) }
        @Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
        get() = throw UnsupportedOperationException()

    var connectionPool: ConnectionPool
        set(value) { builder.connectionPool(value) }
        @Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
        get() = throw UnsupportedOperationException()

    open var dns: Dns
        set(value) { builder.dns(value) }
        @Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
        get() = throw UnsupportedOperationException()

    open var followSslRedirects: Boolean
        set(value) { builder.followSslRedirects(value) }
        @Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
        get() = throw UnsupportedOperationException()

    open var followRedirects: Boolean
        set(value) { builder.followRedirects(value) }
        @Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
        get() = throw UnsupportedOperationException()

    open var retryOnConnectionFailure: Boolean
        set(value) { builder.retryOnConnectionFailure(value) }
        @Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
        get() = throw UnsupportedOperationException()

    open var connectTimeout: Long
        set(value) { builder.connectTimeout(value, TimeUnit.MILLISECONDS) }
        @Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
        get() = throw UnsupportedOperationException()

    open var readTimeout: Long
        set(value) { builder.readTimeout(value, TimeUnit.MILLISECONDS) }
        @Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
        get() = throw UnsupportedOperationException()

    open var writeTimeout: Long
        set(value) { builder.writeTimeout(value, TimeUnit.MILLISECONDS) }
        @Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
        get() = throw UnsupportedOperationException()

    open var pingInterval: Long
        set(value) { builder.pingInterval(value, TimeUnit.MILLISECONDS) }
        @Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
        get() = throw UnsupportedOperationException()

    fun build(): OkHttpClient = builder.build()
}
