package io.github.rybalkinsd.kohttp.ext

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import kotlin.system.measureTimeMillis

/**
 * @author sergey
 */
class StringExtTest {

    @Test
    fun `single sync invoke of httpGet`() {
        val result = "https://www.yandex.ru/search/?text=iphone".httpGet().apply { close() }
        assertThat(result.code()).isEqualTo(200)
    }

    @Test
    fun `single sync invoke of httpGet safe way`() {
        val result = "https://www.yandex.ru/search/?text=iphone".httpGet().apply { close() }

        result.use {
            assertThat(it.code()).isEqualTo(200)
        }
    }

    @Test
    fun `many sync invokes of httpGet`() {
        measureTimeMillis {
            List(100) {
                "https://www.yandex.ru/search/?text=iphone".httpGet().apply { close() }
            }.forEach {
                assertThat(it.code()).isEqualTo(200)
            }
        }.also { println("$it ms") }
    }

}
