package io.github.rybalkinsd.kohttp.configuration

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * @author sergey
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
