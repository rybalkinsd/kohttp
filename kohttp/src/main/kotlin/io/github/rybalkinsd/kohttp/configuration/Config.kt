package io.github.rybalkinsd.kohttp.configuration

import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager

const val DEFAULT_REQUEST_AMOUNT: Int = 256

internal val config = Config()

internal data class Config(val client: ClientConfig = ClientConfig())

internal data class ClientConfig(
    val connectTimeout: Long = TimeUnit.SECONDS.toMillis(10),
    val readTimeout: Long = TimeUnit.SECONDS.toMillis(10),
    val writeTimeout: Long = TimeUnit.SECONDS.toMillis(10),
    val connectionPoolConfig: ConnectionPoolConfig = ConnectionPoolConfig(),
    val followRedirects: Boolean = true,
    val followSslRedirects: Boolean = true,
    val dispatcher: DispatcherConfig = DispatcherConfig()
)

data class SslConfig(
    val sslSocketFactory: SSLSocketFactory,
    val trustManager: X509TrustManager
)

/**
 * okhttp {@link Dispatcher} configuration
 *
 * @since 0.10.0
 * @author evgeny
 */
internal data class DispatcherConfig(
    /**
     * Set the maximum number of requests to execute concurrently.
     *
     * @see okhttp3.Dispatcher.setMaxRequests
     */
    val maxRequests: Int = DEFAULT_REQUEST_AMOUNT,

    /**
     * Set the maximum number of requests for each host to execute concurrently.
     *
     * @see okhttp3.Dispatcher.setMaxRequestsPerHost
     */
    val maxRequestsPerHost: Int = DEFAULT_REQUEST_AMOUNT
)

internal data class ConnectionPoolConfig(
    val maxIdleConnections: Int = 5,
    val keepAliveDuration: Long = TimeUnit.MINUTES.toMillis(5)
)


