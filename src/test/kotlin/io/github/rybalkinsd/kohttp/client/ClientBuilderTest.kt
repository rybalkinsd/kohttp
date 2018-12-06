package io.github.rybalkinsd.kohttp.client

import okhttp3.Authenticator
import okhttp3.CertificatePinner
import okhttp3.ConnectionPool
import okhttp3.ConnectionSpec
import okhttp3.CookieJar
import okhttp3.Dispatcher
import okhttp3.Dns
import okhttp3.EventListener
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.internal.tls.OkHostnameVerifier
import org.junit.Test
import java.net.ProxySelector
import java.util.concurrent.TimeUnit
import javax.net.SocketFactory
import kotlin.test.assertEquals

class ClientBuilderTest {

    @Test
    fun `client regress compare to okhttp`() {
        val defaultDispatcher = Dispatcher()
        val defaultProxy = null
        val defaultProtocols = listOf(Protocol.HTTP_2, Protocol.HTTP_1_1)
        val defaultConnectionSpecs = listOf(ConnectionSpec.MODERN_TLS, ConnectionSpec.CLEARTEXT)
        val defaultFactory = EventListener.Factory { EventListener.NONE }
        val defaultProxySelector = ProxySelector.getDefault()
        val defaultCookieJar = CookieJar.NO_COOKIES
        val defaultSocketFactory = SocketFactory.getDefault()
        val defaultHostnameVerifier = OkHostnameVerifier.INSTANCE
        val defaultCertificatePinner = CertificatePinner.DEFAULT
        val defaultAuth = Authenticator.NONE
        val defaultConnectionPool = ConnectionPool()
        val defaultDns = Dns.SYSTEM
        val defaultTimeout: Long = 20_000

        val dslClient = client {
            dispatcher = defaultDispatcher
            proxy = defaultProxy
            protocols = defaultProtocols
            connectionSpecs = defaultConnectionSpecs
            eventListenerFactory = defaultFactory
            proxySelector = defaultProxySelector
            cookieJar = defaultCookieJar
            socketFactory = defaultSocketFactory
            hostnameVerifier = defaultHostnameVerifier
            certificatePinner = defaultCertificatePinner
            proxyAuthenticator = defaultAuth
            authenticator = defaultAuth
            connectionPool = defaultConnectionPool
            dns = defaultDns
            followSslRedirects = true
            followRedirects = true
            retryOnConnectionFailure = true
            connectTimeout = defaultTimeout
            readTimeout = defaultTimeout
            writeTimeout = defaultTimeout
            pingInterval = 0
        }

        val client = OkHttpClient.Builder()
            .dispatcher(defaultDispatcher)
            .proxy(defaultProxy)
            .protocols(defaultProtocols)
            .connectionSpecs(defaultConnectionSpecs)
            .eventListenerFactory(defaultFactory)
            .proxySelector(defaultProxySelector)
            .cookieJar(defaultCookieJar)
            .socketFactory(defaultSocketFactory)
            .hostnameVerifier(defaultHostnameVerifier)
            .certificatePinner(defaultCertificatePinner)
            .proxyAuthenticator(defaultAuth)
            .authenticator(defaultAuth)
            .connectionPool(defaultConnectionPool)
            .dns(defaultDns)
            .followSslRedirects(true)
            .followRedirects(true)
            .retryOnConnectionFailure(true)
            .connectTimeout(defaultTimeout, TimeUnit.MILLISECONDS)
            .readTimeout(defaultTimeout, TimeUnit.MILLISECONDS)
            .writeTimeout(defaultTimeout, TimeUnit.MILLISECONDS)
            .pingInterval(0, TimeUnit.MILLISECONDS)
            .build()

        with(client) {
            assertEquals(dispatcher(), dslClient.dispatcher())
            assertEquals(authenticator(), dslClient.authenticator())
            assertEquals(protocols(), dslClient.protocols())
            assertEquals(connectionSpecs(), dslClient.connectionSpecs())
            assertEquals(eventListenerFactory(), dslClient.eventListenerFactory())
            assertEquals(proxySelector(), dslClient.proxySelector())
            assertEquals(cookieJar(), dslClient.cookieJar())
            assertEquals(socketFactory(), dslClient.socketFactory())
            assertEquals(hostnameVerifier(), dslClient.hostnameVerifier())
            assertEquals(certificatePinner(), dslClient.certificatePinner())
            assertEquals(proxyAuthenticator(), dslClient.proxyAuthenticator())
            assertEquals(authenticator(), dslClient.authenticator())
            assertEquals(connectionPool(), dslClient.connectionPool())
            assertEquals(dns(), dslClient.dns())
            assertEquals(followSslRedirects(), dslClient.followSslRedirects())
            assertEquals(followSslRedirects(), dslClient.followRedirects())
            assertEquals(retryOnConnectionFailure(), dslClient.retryOnConnectionFailure())
            assertEquals(connectTimeoutMillis(), dslClient.connectTimeoutMillis())
            assertEquals(readTimeoutMillis(), dslClient.readTimeoutMillis())
            assertEquals(writeTimeoutMillis(), dslClient.writeTimeoutMillis())
            assertEquals(pingIntervalMillis(), dslClient.pingIntervalMillis())
        }
    }
}
