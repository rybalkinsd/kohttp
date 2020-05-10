package io.github.rybalkinsd.kohttp.client

import okhttp3.*
import okhttp3.internal.tls.OkHostnameVerifier
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatCode
import org.assertj.core.api.Assertions.fail
import org.junit.Test
import java.lang.reflect.InvocationTargetException
import java.net.ProxySelector
import java.security.KeyStore
import java.security.SecureRandom
import java.util.concurrent.TimeUnit
import javax.net.SocketFactory
import javax.net.ssl.*
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
        val keyStorePath = "identity.jsk"
        val trustStorePath = "truststore.jks"

        val keyStorePassword = "secret".toCharArray()
        val trustStorePassword = "secret".toCharArray()

        val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
        val trustStore = KeyStore.getInstance(KeyStore.getDefaultType())

        ClientBuilderTest::class.java.classLoader.getResourceAsStream(keyStorePath).use { keyStoreInputStream ->
            keyStore.load(keyStoreInputStream, keyStorePassword)
        }

        ClientBuilderTest::class.java.classLoader.getResourceAsStream(trustStorePath).use { trustStoreInputStream ->
            trustStore.load(trustStoreInputStream, trustStorePassword)
        }

        val keyManagerFactory: KeyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm())
        keyManagerFactory.init(keyStore, keyStorePassword)
        val keyManagers: Array<KeyManager> = keyManagerFactory.keyManagers

        val trustManagerFactory: TrustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        trustManagerFactory.init(trustStore)
        val trustManagers: Array<TrustManager> = trustManagerFactory.trustManagers

        val sslContext = SSLContext.getInstance("TLSv1.2")
        sslContext.init(keyManagers, trustManagers, SecureRandom())

        assertThatCode {
            client {
                sslSocketFactory = sslContext.socketFactory
                trustManager = trustManagers[0] as X509TrustManager
            }
        }.doesNotThrowAnyException()
    }

}
