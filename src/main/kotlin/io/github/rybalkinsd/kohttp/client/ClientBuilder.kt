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
import okhttp3.Protocol
import java.net.Proxy
import java.net.ProxySelector
import javax.net.SocketFactory
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSocketFactory


interface ClientBuilder : ForkClientBuilder {
    var dispatcher: Dispatcher
    var proxy: Proxy?
    var protocols: List<Protocol>
    var connectionSpecs: List<ConnectionSpec>
    var eventListenerFactory: EventListener.Factory
    var proxySelector: ProxySelector
    var cookieJar: CookieJar
    var socketFactory: SocketFactory
    var sslSocketFactory: SSLSocketFactory
    var hostnameVerifier: HostnameVerifier
    var certificatePinner: CertificatePinner
    var proxyAuthenticator: Authenticator
    var authenticator: Authenticator
    var connectionPool: ConnectionPool
}

interface ForkClientBuilder {
    var interceptors: List<Interceptor>
    var networkInterceptors: List<Interceptor>
    var cache: Cache?
    var dns: Dns
    var followSslRedirects: Boolean
    var followRedirects: Boolean
    var retryOnConnectionFailure: Boolean
    var connectTimeout: Long
    var readTimeout: Long
    var writeTimeout: Long
    var pingInterval: Long
}
