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
import java.rmi.NoSuchObjectException
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier

class ForkClientBuilderTest {
    fun `fork client regress compare to okhttp`() {

        val defaultTimeout = 100L
        val client = CommonHttpClient.newBuilder()
            .readTimeout(defaultTimeout, TimeUnit.MILLISECONDS)
            .writeTimeout(defaultTimeout, TimeUnit.MILLISECONDS)
            .pingInterval(defaultTimeout, TimeUnit.MILLISECONDS)
            .retryOnConnectionFailure(false)
            .followRedirects(false)
            .followSslRedirects(false)
            .dns { emptyList() }
            .
            .build()

        val dslClient = CommonHttpClient.fork {
            proxy = Proxy.NO_PROXY
            interceptors = emptyList()
            networkInterceptors = emptyList()
            proxySelector = object: ProxySelector() {
                override fun select(uri: URI?): MutableList<Proxy> = mutableListOf()
                override fun connectFailed(uri: URI?, sa: SocketAddress?, ioe: IOException?) { }
            }
            cookieJar = object : CookieJar {
                override fun saveFromResponse(url: HttpUrl, cookies: MutableList<Cookie>) { }
                override fun loadForRequest(url: HttpUrl): MutableList<Cookie> = mutableListOf()
            }
            cache = null

            hostnameVerifier = HostnameVerifier { _, _ -> false }
            certificatePinner = CertificatePinner.Builder().build()
            proxyAuthenticator = Authenticator.NONE
            authenticator = Authenticator.NONE
            dns = Dns { emptyList() }
            followSslRedirects = false
            followRedirects = false
            retryOnConnectionFailure = false
            connectTimeout = defaultTimeout
            readTimeout = defaultTimeout
            writeTimeout = defaultTimeout
            pingInterval = defaultTimeout


            connectionSpecs = emptyList()
            protocols = emptyList()
        }

    }
}