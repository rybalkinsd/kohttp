package io.github.rybalkinsd.kohttp.dsl

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test


/**
 * @author sergey, alex, gokul, hakky54
 */
class HttpDeleteDslKtTest {

    @Test
    fun `single sync http delete invocation`() {
        val response = httpDelete {
            host = "postman-echo.com"
            path = "/delete"
        }

        response.use {
            assertThat(it.code()).isEqualTo(200)
        }
    }

}
