package io.github.rybalkinsd.kohttp.client

import okhttp3.Dns
import org.assertj.core.api.Assertions.assertThat
import java.net.InetAddress
import java.util.concurrent.TimeUnit
import kotlin.test.Test

/**
 * @author sergey
 */
class ForkClientBuilderTest {

    @Test
    fun `fork client regress compare to okhttp`() {
        val defaultTimeout = 100L
        val defaultDns = DefaultDns()

        val client = defaultHttpClient.newBuilder()
            .cache(null)
            .dns(defaultDns)
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
            followRedirects = false
            retryOnConnectionFailure = false
            connectTimeout = defaultTimeout
            readTimeout = defaultTimeout
            writeTimeout = defaultTimeout
            pingInterval = defaultTimeout
        }

        with(dslClient) {
            assertThat(dispatcher).isEqualTo(client.dispatcher)
            assertThat(authenticator).isEqualTo(client.authenticator)
            assertThat(protocols).isEqualTo(client.protocols)
            assertThat(connectionSpecs).isEqualTo(client.connectionSpecs)
            assertThat(eventListenerFactory).isEqualTo(client.eventListenerFactory)
            assertThat(proxySelector).isEqualTo(client.proxySelector)
            assertThat(cookieJar).isEqualTo(client.cookieJar)
            assertThat(socketFactory).isEqualTo(client.socketFactory)
            assertThat(hostnameVerifier).isEqualTo(client.hostnameVerifier)
            assertThat(certificatePinner).isEqualTo(client.certificatePinner)
            assertThat(proxyAuthenticator).isEqualTo(client.proxyAuthenticator)
            assertThat(authenticator).isEqualTo(client.authenticator)
            assertThat(connectionPool).isEqualTo(client.connectionPool)
            assertThat(dns).isEqualTo(client.dns)
            assertThat(followRedirects).isEqualTo(client.followRedirects)
            assertThat(retryOnConnectionFailure).isEqualTo(client.retryOnConnectionFailure)
            assertThat(connectTimeoutMillis).isEqualTo(client.connectTimeoutMillis)
            assertThat(readTimeoutMillis).isEqualTo(client.readTimeoutMillis)
            assertThat(writeTimeoutMillis).isEqualTo(client.writeTimeoutMillis)
            assertThat(pingIntervalMillis).isEqualTo(client.pingIntervalMillis)
        }
    }

    private class DefaultDns : Dns {
        override fun lookup(hostname: String): List<InetAddress> {
            return emptyList()
        }
    }
}