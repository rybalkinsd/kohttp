package io.github.rybalkinsd.kohttp.client

import io.github.rybalkinsd.kohttp.ext.httpGet
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.net.SocketTimeoutException
import kotlin.test.assertFailsWith

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
            assertThat(it.code()).isEqualTo(200)
        }
    }

}
