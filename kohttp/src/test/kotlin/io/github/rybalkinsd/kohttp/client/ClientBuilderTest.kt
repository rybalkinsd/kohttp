package io.github.rybalkinsd.kohttp.client

import io.github.rybalkinsd.kohttp.configuration.SslConfig
import nl.altindag.sslcontext.SSLFactory
import okhttp3.*
import okhttp3.internal.tls.OkHostnameVerifier
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatCode
import org.assertj.core.api.Assertions.fail
import org.junit.Test
import java.lang.reflect.InvocationTargetException
import java.net.ProxySelector
import java.util.concurrent.TimeUnit
import javax.net.SocketFactory
import kotlin.reflect.full.declaredMemberProperties


/**
 * @author sergey
 */
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

        with(dslClient) {
            assertThat(dispatcher()).isEqualTo(client.dispatcher())
            assertThat(authenticator()).isEqualTo(client.authenticator())
            assertThat(protocols()).isEqualTo(client.protocols())
            assertThat(connectionSpecs()).isEqualTo(client.connectionSpecs())
            assertThat(eventListenerFactory()).isEqualTo(client.eventListenerFactory())
            assertThat(proxySelector()).isEqualTo(client.proxySelector())
            assertThat(cookieJar()).isEqualTo(client.cookieJar())
            assertThat(socketFactory()).isEqualTo(client.socketFactory())
            assertThat(socketFactory()).isEqualTo(client.socketFactory())
            assertThat(hostnameVerifier()).isEqualTo(client.hostnameVerifier())
            assertThat(certificatePinner()).isEqualTo(client.certificatePinner())
            assertThat(proxyAuthenticator()).isEqualTo(client.proxyAuthenticator())
            assertThat(authenticator()).isEqualTo(client.authenticator())
            assertThat(connectionPool()).isEqualTo(client.connectionPool())
            assertThat(dns()).isEqualTo(client.dns())
            assertThat(followSslRedirects()).isEqualTo(client.followSslRedirects())
            assertThat(followSslRedirects()).isEqualTo(client.followRedirects())
            assertThat(retryOnConnectionFailure()).isEqualTo(client.retryOnConnectionFailure())
            assertThat(connectTimeoutMillis()).isEqualTo(client.connectTimeoutMillis())
            assertThat(readTimeoutMillis()).isEqualTo(client.readTimeoutMillis())
            assertThat(writeTimeoutMillis()).isEqualTo(client.writeTimeoutMillis())
            assertThat(pingIntervalMillis()).isEqualTo(client.pingIntervalMillis())
        }
    }

    @Test
    fun `client builder getters throw exceptions`() {
        val clientBuilder = ClientBuilderImpl()
        ClientBuilderImpl::class.declaredMemberProperties.filter { !it.isFinal }
            .map {
                try {
                    it.call(clientBuilder)
                    fail<Any>("${it.name} call is successful, but not expected to be")
                } catch (ignored: InvocationTargetException) {
                }
            }
    }

    @Test
    fun `client with ssl material`() {
        val sslFactory = SSLFactory.builder()
                .withIdentity("keystores/identity.jks", "secret".toCharArray())
                .withTrustStore("keystores/truststore.jks", "secret".toCharArray())
                .build()

        assertThatCode {
            client {
                sslConfig = SslConfig(
                        sslSocketFactory = sslFactory.sslContext.socketFactory,
                        trustManager = sslFactory.trustManager
                )
                hostnameVerifier = sslFactory.hostnameVerifier
            }
        }.doesNotThrowAnyException()
    }

}
