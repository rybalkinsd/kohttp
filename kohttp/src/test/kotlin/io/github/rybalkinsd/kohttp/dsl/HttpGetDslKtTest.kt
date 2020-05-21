package io.github.rybalkinsd.kohttp.dsl

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test


/**
 * @author sergey, alex, gokul, hakky54
 */
class HttpGetDslKtTest {

    @Test
    fun `single sync http get invocation`() {
        val response = httpGet {
            host = "postman-echo.com"
            path = "/get"
        }

        response.use {
            assertThat(it.code()).isEqualTo(200)
        }
    }

}
