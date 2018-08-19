package com.kohttp.configuration

import org.junit.Test
import kotlin.test.assertEquals

/**
 * Created by Sergey on 19/08/2018.
 */
class ConfigTest {

    @Test
    fun `client full configuration`() {
        with(Config.instance.client) {
            assertEquals(5_000, connectTimeout)
            assertEquals(10_000, readTimeout)
            assertEquals(42, writeTimeout)
            assertEquals(false, followRedirects)
            assertEquals(false, followSslRedirects)
            assertEquals(42, connectionPool.maxIdleConnections)
            assertEquals(321, connectionPool.keepAliveDuration)
        }
    }
}