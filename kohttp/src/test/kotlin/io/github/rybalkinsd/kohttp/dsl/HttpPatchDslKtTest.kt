package io.github.rybalkinsd.kohttp.dsl

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test


/**
 * @author sergey, alex, gokul, hakky54
 */
class HttpPatchDslKtTest {

    @Test
    fun `single sync http patch invocation`() {
        val response = httpPatch {
            host = "postman-echo.com"
            path = "/patch"
        }

        response.use {
            assertThat(it.code()).isEqualTo(200)
        }
    }

}
