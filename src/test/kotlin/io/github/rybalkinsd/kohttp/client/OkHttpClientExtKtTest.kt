package io.github.rybalkinsd.kohttp.client

import io.github.rybalkinsd.kohttp.ext.httpGet
import org.junit.Test
import java.net.SocketTimeoutException
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

/**
 * Created by Sergey Rybalkin on 26/11/2018.
 */
class OkHttpClientExtKtTest {

    @Test
    fun fork() {
        val impatientClient = DefaultHttpClient.fork {
            readTimeout = 500
        }

        val patientClient = impatientClient.fork {
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
