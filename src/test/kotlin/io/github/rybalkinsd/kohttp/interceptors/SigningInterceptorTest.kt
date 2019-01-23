package io.github.rybalkinsd.kohttp.interceptors

import io.github.rybalkinsd.kohttp.client.defaultHttpClient
import io.github.rybalkinsd.kohttp.client.fork
import io.github.rybalkinsd.kohttp.dsl.httpGet
import io.github.rybalkinsd.kohttp.util.asJson
import org.junit.Test
import java.security.MessageDigest
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SigningInterceptorTest {
    @Test
    fun `signs request with MD5 algorithm`() {
        val urlEncoder = Base64.getUrlEncoder()
        val algorithm = MessageDigest.getInstance("md5")

        val client = defaultHttpClient.fork {
            interceptors = listOf(SigningInterceptor("key") {
                val query = (query() ?: "").toByteArray()
                urlEncoder.encodeToString(algorithm.digest(query))
            })
        }

        val s = "foo=bar&random=213".toByteArray(Charsets.UTF_8)
        val expected = urlEncoder.encodeToString(MessageDigest.getInstance("md5").digest(s))

        httpGet(client = client) {
            host = "postman-echo.com"
            path = "/get"

            param {
                "foo" to "bar"
                "random" to 213
            }
        }.use {
            val parsedResponse = it.body()?.string().asJson()
            assertTrue { it.code() == 200 }
            assertEquals(expected, parsedResponse["args"]["key"].asText())
        }
    }
}
