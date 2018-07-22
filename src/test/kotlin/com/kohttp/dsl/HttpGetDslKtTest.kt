package com.kohttp.dsl

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

/**
 * Created by sergey on 22/07/2018.
 */
class HttpGetDslKtTest {

    @Test
    fun `single sync http get invocation`() {
        val response = httpGet {
            host = "yandex.ru"
            path = "/search"

            param {
                "text" to "iphone"
                "lr" to 213
            }
        }

        assertNotNull(response)
        assertEquals(200, response?.code())
    }
}