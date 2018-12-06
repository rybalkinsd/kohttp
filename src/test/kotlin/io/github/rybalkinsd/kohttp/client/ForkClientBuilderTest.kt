package io.github.rybalkinsd.kohttp.client

import okhttp3.Authenticator
import okhttp3.CertificatePinner
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.Dns
import okhttp3.HttpUrl
import java.io.IOException
import java.net.Proxy
import java.net.ProxySelector
import java.net.SocketAddress
import java.net.URI
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import kotlin.test.Test
import kotlin.test.assertEquals

class ForkClientBuilderTest {

    @Test
    fun `fork client regress compare to okhttp`() {
        val defaultTimeout = 100L
        val defaultHostNameVerifier = HostnameVerifier { _, _ -> false }
        val defaultDns = Dns { emptyList() }
        val defaultProxySelector = object: ProxySelector() {
            override fun select(uri: URI?): MutableList<Proxy> = mutableListOf()
            override fun connectFailed(uri: URI?, sa: SocketAddress?, ioe: IOException?) { }
        }
        val defaultCookieJar = object : CookieJar {
            override fun saveFromResponse(url: HttpUrl, cookies: MutableList<Cookie>) { }
            override fun loadForRequest(url: HttpUrl): MutableList<Cookie> = mutableListOf()
        }
        val defaultCertificatePinner = CertificatePinner.Builder().build()

        val client = defaultHttpClient.newBuilder()
            .proxy(Proxy.NO_PROXY)
            .proxySelector(defaultProxySelector)
            .cookieJar(defaultCookieJar)
            .cache(null)
            .hostnameVerifier(defaultHostNameVerifier)
            .certificatePinner(defaultCertificatePinner)
            .proxyAuthenticator(Authenticator.NONE)
            .authenticator(Authenticator.NONE)
            .dns(defaultDns)
            .followSslRedirects(false)
            .followRedirects(false)
            .retryOnConnectionFailure(false)
            .connectTimeout(defaultTimeout, TimeUnit.MILLISECONDS)
            .readTimeout(defaultTimeout, TimeUnit.MILLISECONDS)
            .writeTimeout(defaultTimeout, TimeUnit.MILLISECONDS)
            .pingInterval(defaultTimeout, TimeUnit.MILLISECONDS)
            .connectionSpecs(emptyList())
            .build()

        val dslClient = defaultHttpClient.fork {
            proxy = Proxy.NO_PROXY
            interceptors = emptyList()
            networkInterceptors = emptyList()
            proxySelector = defaultProxySelector
            cookieJar = defaultCookieJar
            cache = null
            hostnameVerifier = defaultHostNameVerifier
            certificatePinner = defaultCertificatePinner
            proxyAuthenticator = Authenticator.NONE
            authenticator = Authenticator.NONE
            dns = defaultDns
            followSslRedirects = false
            followRedirects = false
            retryOnConnectionFailure = false
            connectTimeout = defaultTimeout
            readTimeout = defaultTimeout
            writeTimeout = defaultTimeout
            pingInterval = defaultTimeout
            connectionSpecs = emptyList()
        }

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