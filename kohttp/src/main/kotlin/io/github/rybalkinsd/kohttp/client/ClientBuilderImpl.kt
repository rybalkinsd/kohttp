package io.github.rybalkinsd.kohttp.client

import io.github.rybalkinsd.kohttp.configuration.SslConfig
import okhttp3.Authenticator
import okhttp3.Cache
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

/**
 * DSL builder for OkHttpClient
 *
 * @since 0.5.0
 * @author sergey
 */
fun client(block: ClientBuilder.() -> Unit) = ClientBuilderImpl().apply(block).build()

@Suppress("OverridingDeprecatedMember")
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
        get() = throw UnsupportedOperationException()

    override var proxy: Proxy?
        set(value) { builder.proxy(value) }
        get() = throw UnsupportedOperationException()

    override var protocols: List<Protocol>
        set(value) { builder.protocols(value) }
        get() = throw UnsupportedOperationException()

    override var connectionSpecs: List<ConnectionSpec>
        set(value) { builder.connectionSpecs(value) }
        get() = throw UnsupportedOperationException()

    override var interceptors: List<Interceptor>
        set(value) { value.forEach { builder.addInterceptor(it) } }
        get() = throw UnsupportedOperationException()

    override var networkInterceptors: List<Interceptor>
        set(value) { value.forEach { builder.addNetworkInterceptor(it) } }
        get() = throw UnsupportedOperationException()

    override var eventListenerFactory: EventListener.Factory
        set(value) { builder.eventListenerFactory(value) }
        get() = throw UnsupportedOperationException()

    override var proxySelector: ProxySelector
        set(value) { builder.proxySelector(value) }
        get() = throw UnsupportedOperationException()

    override var cookieJar: CookieJar
        set(value) { builder.cookieJar(value) }
        get() = throw UnsupportedOperationException()

    override var cache: Cache?
        set(value) { builder.cache(value) }
        get() = throw UnsupportedOperationException()

    override var sslConfig: SslConfig
        set(value) {
            if (value.sslSocketFactory != null && value.trustManager != null) {
                builder.sslSocketFactory(value.sslSocketFactory!!, value.trustManager!!)
            }

            value.socketFactory.let { if (it != null) builder.socketFactory(it) }
            value.certificatePinner.let { if (it != null) builder.certificatePinner(it) }
            value.hostnameVerifier.let { if (it != null) builder.hostnameVerifier(it) }
            value.followSslRedirects.let { if (it != null) builder.followSslRedirects(it) }
        }
        get() = throw UnsupportedOperationException()

    override var proxyAuthenticator: Authenticator
        set(value) { builder.proxyAuthenticator(value) }
        get() = throw UnsupportedOperationException()

    override var authenticator: Authenticator
        set(value) { builder.authenticator(value) }
        get() = throw UnsupportedOperationException()

    override var connectionPool: ConnectionPool
        set(value) { builder.connectionPool(value) }
        get() = throw UnsupportedOperationException()

    override var dns: Dns
        set(value) { builder.dns(value) }
        get() = throw UnsupportedOperationException()

    override var followSslRedirects: Boolean
        set(value) { builder.followSslRedirects(value) }
        get() = throw UnsupportedOperationException()

    override var followRedirects: Boolean
        set(value) { builder.followRedirects(value) }
        get() = throw UnsupportedOperationException()

    override var retryOnConnectionFailure: Boolean
        set(value) { builder.retryOnConnectionFailure(value) }
        get() = throw UnsupportedOperationException()

    override var connectTimeout: Long
        set(value) { builder.connectTimeout(value, TimeUnit.MILLISECONDS) }
        get() = throw UnsupportedOperationException()

    override var readTimeout: Long
        set(value) { builder.readTimeout(value, TimeUnit.MILLISECONDS) }
        get() = throw UnsupportedOperationException()

    override var writeTimeout: Long
        set(value) { builder.writeTimeout(value, TimeUnit.MILLISECONDS) }
        get() = throw UnsupportedOperationException()

    override var pingInterval: Long
        set(value) { builder.pingInterval(value, TimeUnit.MILLISECONDS) }
        get() = throw UnsupportedOperationException()

    fun build(): OkHttpClient = builder.build()

}
