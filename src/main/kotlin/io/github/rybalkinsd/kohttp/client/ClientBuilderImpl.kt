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
 * DSL builder for OkHttpClient
 */
fun client(block: ClientBuilder.() -> Unit) = ClientBuilderImpl().apply(block).build()

/**
 *
 * Migrate getters to private when
 * https://youtrack.jetbrains.com/issue/KT-3110
 * will be implemented
 *
 */
internal class ClientBuilderImpl : ClientBuilder {
    private val builder: OkHttpClient.Builder

    constructor() {
        builder = OkHttpClient.Builder()
    }

    constructor(client: OkHttpClient) {
        builder = client.newBuilder()
    }

    override var dispatcher: Dispatcher
        set(value) { builder.dispatcher(value) }
        @Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
        get() = throw UnsupportedOperationException()

    override var proxy: Proxy?
        set(value) { builder.proxy(value) }
        @Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
        get() = throw UnsupportedOperationException()

    override var protocols: List<Protocol>
        set(value) { builder.protocols(value) }
        @Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
        get() = throw UnsupportedOperationException()

    override var connectionSpecs: List<ConnectionSpec>
        set(value) { builder.connectionSpecs(value) }
        @Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
        get() = throw UnsupportedOperationException()

    override var interceptors: List<Interceptor>
        set(value) { value.forEach { builder.addInterceptor(it) } }
        @Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
        get() = throw UnsupportedOperationException()

    override var networkInterceptors: List<Interceptor>
        set(value) { value.forEach { builder.addNetworkInterceptor(it) } }
        @Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
        get() = throw UnsupportedOperationException()

    override var eventListenerFactory: EventListener.Factory
        set(value) { builder.eventListenerFactory(value) }
        @Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
        get() = throw UnsupportedOperationException()

    override var proxySelector: ProxySelector
        set(value) { builder.proxySelector(value) }
        @Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
        get() = throw UnsupportedOperationException()

    override var cookieJar: CookieJar
        set(value) { builder.cookieJar(value) }
        @Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
        get() = throw UnsupportedOperationException()

    override var cache: Cache?
        set(value) { builder.cache(value) }
        @Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
        get() = throw UnsupportedOperationException()

    override var socketFactory: SocketFactory
        set(value) { builder.socketFactory(value) }
        @Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
        get() = throw UnsupportedOperationException()

    override var sslSocketFactory: SSLSocketFactory
        set(value) { builder.sslSocketFactory(value) }
        @Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
        get() = throw UnsupportedOperationException()

    override var hostnameVerifier: HostnameVerifier
        set(value) { builder.hostnameVerifier(value) }
        @Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
        get() = throw UnsupportedOperationException()

    override var certificatePinner: CertificatePinner
        set(value) { builder.certificatePinner(value) }
        @Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
        get() = throw UnsupportedOperationException()

    override var proxyAuthenticator: Authenticator
        set(value) { builder.proxyAuthenticator(value) }
        @Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
        get() = throw UnsupportedOperationException()

    override var authenticator: Authenticator
        set(value) { builder.authenticator(value) }
        @Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
        get() = throw UnsupportedOperationException()

    override var connectionPool: ConnectionPool
        set(value) { builder.connectionPool(value) }
        @Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
        get() = throw UnsupportedOperationException()

    override var dns: Dns
        set(value) { builder.dns(value) }
        @Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
        get() = throw UnsupportedOperationException()

    override var followSslRedirects: Boolean
        set(value) { builder.followSslRedirects(value) }
        @Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
        get() = throw UnsupportedOperationException()

    override var followRedirects: Boolean
        set(value) { builder.followRedirects(value) }
        @Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
        get() = throw UnsupportedOperationException()

    override var retryOnConnectionFailure: Boolean
        set(value) { builder.retryOnConnectionFailure(value) }
        @Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
        get() = throw UnsupportedOperationException()

    override var connectTimeout: Long
        set(value) { builder.connectTimeout(value, TimeUnit.MILLISECONDS) }
        @Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
        get() = throw UnsupportedOperationException()

    override var readTimeout: Long
        set(value) { builder.readTimeout(value, TimeUnit.MILLISECONDS) }
        @Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
        get() = throw UnsupportedOperationException()

    override var writeTimeout: Long
        set(value) { builder.writeTimeout(value, TimeUnit.MILLISECONDS) }
        @Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
        get() = throw UnsupportedOperationException()

    override var pingInterval: Long
        set(value) { builder.pingInterval(value, TimeUnit.MILLISECONDS) }
        @Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
        get() = throw UnsupportedOperationException()

    fun build(): OkHttpClient = builder.build()
}
