package io.github.rybalkinsd.kohttp.client

import io.github.rybalkinsd.kohttp.client.dsl.InterceptorsDsl
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
import okhttp3.Protocol
import java.net.Proxy
import java.net.ProxySelector
import javax.net.SocketFactory


/**
 *
 * Migrate `@get:Deprecated` to private getters when
 * https://youtrack.jetbrains.com/issue/KT-3110
 * will be implemented
 *
 * @since 0.5.0
 * @author sergey
 */
interface ClientBuilder : ForkClientBuilder {
    @get:Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
    var dispatcher: Dispatcher

    @get:Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
    var proxy: Proxy?

    @get:Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
    var protocols: List<Protocol>

    @get:Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
    var connectionSpecs: List<ConnectionSpec>

    @get:Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
    var eventListenerFactory: EventListener.Factory

    @get:Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
    var proxySelector: ProxySelector

    @get:Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
    var cookieJar: CookieJar

    @get:Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
    var socketFactory: SocketFactory

    @get:Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
    var sslConfig: SslConfig

    @get:Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
    var proxyAuthenticator: Authenticator

    @get:Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
    var authenticator: Authenticator

    @get:Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
    var connectionPool: ConnectionPool
}

interface ForkClientBuilder {
    @get:Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
    var interceptors: List<Interceptor>

    @get:Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
    var networkInterceptors: List<Interceptor>

    @get:Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
    var cache: Cache?

    @get:Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
    var dns: Dns

    @get:Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
    var followRedirects: Boolean

    @get:Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
    var retryOnConnectionFailure: Boolean

    @get:Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
    var connectTimeout: Long

    @get:Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
    var readTimeout: Long

    @get:Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
    var writeTimeout: Long

    @get:Deprecated(level = DeprecationLevel.ERROR, message = "Write only field")
    var pingInterval: Long

    fun interceptors(block: InterceptorsDsl.() -> Unit) {
        interceptors = InterceptorsDsl().apply(block).list()
    }
}
