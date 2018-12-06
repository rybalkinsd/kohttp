package io.github.rybalkinsd.kohttp.client

import okhttp3.Dns
import java.util.concurrent.TimeUnit
import kotlin.test.Test
import kotlin.test.assertEquals

class ForkClientBuilderTest {

    @Test
    fun `fork client regress compare to okhttp`() {
        val defaultTimeout = 100L
        val defaultDns = Dns { emptyList() }

        val client = defaultHttpClient.newBuilder()
            .cache(null)
            .dns(defaultDns)
            .followSslRedirects(false)
            .followRedirects(false)
            .retryOnConnectionFailure(false)
            .connectTimeout(defaultTimeout, TimeUnit.MILLISECONDS)
            .readTimeout(defaultTimeout, TimeUnit.MILLISECONDS)
            .writeTimeout(defaultTimeout, TimeUnit.MILLISECONDS)
            .pingInterval(defaultTimeout, TimeUnit.MILLISECONDS)
            .build()

        val dslClient = defaultHttpClient.fork {
            interceptors = emptyList()
            networkInterceptors = emptyList()
            cache = null
            dns = defaultDns
            followSslRedirects = false
            followRedirects = false
            retryOnConnectionFailure = false
            connectTimeout = defaultTimeout
            readTimeout = defaultTimeout
            writeTimeout = defaultTimeout
            pingInterval = defaultTimeout
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