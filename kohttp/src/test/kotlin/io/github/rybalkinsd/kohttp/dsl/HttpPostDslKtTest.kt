package io.github.rybalkinsd.kohttp.dsl

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test


/**
 * @author sergey, alex, gokul, hakky54
 */
class HttpPostDslKtTest {

    @Test
    fun `single sync http post invocation`() {
        val response = httpPost {
            host = "postman-echo.com"
            path = "/post"
        }

        response.use {
            assertThat(it.code()).isEqualTo(200)
        }
    }

}
