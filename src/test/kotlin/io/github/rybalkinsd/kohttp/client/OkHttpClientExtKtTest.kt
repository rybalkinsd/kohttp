package io.github.rybalkinsd.kohttp.client

import io.github.rybalkinsd.kohttp.ext.httpGet
import org.junit.Test
import java.net.SocketTimeoutException
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

/**
 * @author sergey
 */
class OkHttpClientExtKtTest {

    @Test
    fun fork() {
        val impatientClient = defaultHttpClient.fork {
            readTimeout = 500
        }

        val patientClient = defaultHttpClient.fork {
            readTimeout = 3_500
        }

        assertFailsWith<SocketTimeoutException> {
            "https://postman-echo.com/delay/3".httpGet(client = impatientClient)
        }

        "https://postman-echo.com/delay/3".httpGet(client = patientClient).use {
            assertTrue { it.code() == 200 }
        }
    }

}
