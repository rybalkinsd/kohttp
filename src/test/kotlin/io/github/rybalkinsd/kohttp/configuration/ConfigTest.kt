package io.github.rybalkinsd.kohttp.configuration

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Created by Sergey on 19/08/2018.
 */
class ConfigTest {

    @Test
    fun `client full configuration`() {
        with(config.client) {
            assertEquals(5_000, connectTimeout)
            assertEquals(10_000, readTimeout)
            assertEquals(10_000, writeTimeout)
            assertTrue { followRedirects }
            assertTrue { followSslRedirects }
            assertEquals(42, connectionPoolConfig.maxIdleConnections)
            assertEquals(10_000, connectionPoolConfig.keepAliveDuration)
        }
    }
}
