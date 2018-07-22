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
        val result = httpGet {
            host = "yandex.ru"
            path = "/search"

            param {
                "text" to "qqq"
                "lr" to 213
            }
        }

        assertNotNull(result)
        assertEquals(200, result?.code())
    }
}