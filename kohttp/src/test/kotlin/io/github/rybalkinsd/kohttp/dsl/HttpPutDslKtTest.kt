package io.github.rybalkinsd.kohttp.dsl

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test


/**
 * @author sergey, alex, gokul, hakky54
 */
class HttpPutDslKtTest {

    @Test
    fun `single sync http put invocation`() {
        val response = httpPut {
            host = "postman-echo.com"
            path = "/put"
        }

        response.use {
            assertThat(it.code()).isEqualTo(200)
        }
    }

}
