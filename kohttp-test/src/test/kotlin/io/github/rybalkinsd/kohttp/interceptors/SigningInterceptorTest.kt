package io.github.rybalkinsd.kohttp.interceptors

import io.github.rybalkinsd.kohttp.client.defaultHttpClient
import io.github.rybalkinsd.kohttp.client.fork
import io.github.rybalkinsd.kohttp.dsl.httpGet
import io.github.rybalkinsd.kohttp.jackson.ext.toJson
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.security.MessageDigest
import java.util.*

class SigningInterceptorTest {
    @Test
    fun `signs request with MD5 algorithm`() {
        val urlEncoder = Base64.getUrlEncoder()
        val md5 = MessageDigest.getInstance("md5")

        val client = defaultHttpClient.fork {
            interceptors {
                +SigningInterceptor("key") {
                    val query = (query() ?: "").toByteArray()
                    urlEncoder.encodeToString(md5.digest(query))
                }
            }
        }

        val s = "foo=bar&random=213".toByteArray(Charsets.UTF_8)
        val expected = urlEncoder.encodeToString(md5.digest(s))

        httpGet(client = client) {
            host = "postman-echo.com"
            path = "/get"

            param {
                "foo" to "bar"
                "random" to 213
            }
        }.use {
            val parsedResponse = it.toJson()
            assertThat(it.code()).isEqualTo(200)
            assertThat(parsedResponse["args"]["key"].asText()).isEqualTo(expected)
        }
    }
}
