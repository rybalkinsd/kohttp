package io.github.rybalkinsd.kohttp.mockk

import io.github.rybalkinsd.kohttp.dsl.httpGet
import io.github.rybalkinsd.kohttp.dsl.httpPost
import io.github.rybalkinsd.kohttp.ext.url
import io.github.rybalkinsd.kohttp.mockk.Matchers.Companion.matching
import io.mockk.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class MatchersTest {

    @Test
    fun `get request matcher`() {
        every {
            httpGet(init = matching {
                url("https://unknown.site")
                param {
                    "x" to 123
                    "y" to "no"
                }
            })
        } returns Response {
            request(mockk())
            protocol(mockk())
            code(42)
            message("")
        }


        val r = httpGet {
            url("https://unknown.site")
            param {
                "y" to "no"
                "x" to 123
            }
        }
        assertThat(r.code()).isEqualTo(42)
    }

    @Test
    fun `post request matcher`() {
        every {
            httpPost(init = matching {
                url("https://github.com")
                body {
                    json {
                        "abc" to 123
                    }
                }
            })
        } returns Response {
            request(mockk())
            protocol(mockk())
            code(101)
            message("mockk")
        }

        val r = httpPost {
            url("https://github.com")
            body {
                json {
                    "abc" to 123
                }
            }
        }
        with(r) {
            assertThat(message()).isEqualTo("mockk")
            assertThat(code()).isEqualTo(101)
        }
    }

}
