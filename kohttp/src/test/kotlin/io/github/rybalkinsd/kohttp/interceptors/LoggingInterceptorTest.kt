package io.github.rybalkinsd.kohttp.interceptors

import io.github.rybalkinsd.kohttp.client.defaultHttpClient
import io.github.rybalkinsd.kohttp.client.fork
import io.github.rybalkinsd.kohttp.dsl.upload
import io.github.rybalkinsd.kohttp.ext.httpGet
import io.github.rybalkinsd.kohttp.interceptors.logging.CurlLoggingInterceptor
import io.github.rybalkinsd.kohttp.interceptors.logging.HttpLoggingInterceptor
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class LoggingInterceptorTest {
    @Test
    fun `calls passed logging method with message`() {
        var logSize = 0
        val client = defaultHttpClient.fork {
            interceptors {
                +HttpLoggingInterceptor {
                    logSize += it.length
                }
            }
        }

        "https://postman-echo.com/get".httpGet(client)

        assertThat(logSize).isGreaterThan(0)
    }

    @Test
    fun `default logging happens without exceptions`() {
        val client = defaultHttpClient.fork {
            interceptors {
                +HttpLoggingInterceptor()
            }
        }

        val response = "https://postman-echo.com/get".httpGet(client)

        assertThat(response.code()).isEqualTo(200)
    }

    @Test
    fun `default logging happens without exceptions when we have response body`() {
        val client = defaultHttpClient.fork {
            interceptors {
                +HttpLoggingInterceptor()
            }
        }

        val fileUri = this.javaClass.getResource("/cat.gif").toURI()
        val response = upload(client) {
            url("http://postman-echo.com/post")
            file(fileUri)
        }

        assertThat(response.code()).isEqualTo(200)
    }

    @Test
    fun `curl command logging happens without exceptions `() {
        val client = defaultHttpClient.fork {
            interceptors {
                +CurlLoggingInterceptor()
            }
        }

        val response = "https://postman-echo.com/get".httpGet(client)

        assertThat(response.code()).isEqualTo(200)
    }
}
