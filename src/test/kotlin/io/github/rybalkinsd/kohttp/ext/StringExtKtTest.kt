package io.github.rybalkinsd.kohttp.ext

import org.junit.Test
import kotlin.system.measureTimeMillis
import kotlin.test.assertEquals

/**
 * Created by Sergey on 21/07/2018.
 */
class StringExtKtTest {

    @Test
    fun `single sync invoke of httpGet`() {
        val result = "https://www.yandex.ru/search/?text=iphone".httpGet().apply { close() }
        assertEquals(200, result.code())
    }

    @Test
    fun `single sync invoke of httpGet safe way`() {
        val result = "https://www.yandex.ru/search/?text=iphone".httpGet().apply { close() }

        result.use {
            assertEquals(200, it.code())
        }
    }

    @Test
    fun `many sync invokes of httpGet`() {
        measureTimeMillis {
            List(100) {
                "https://www.yandex.ru/search/?text=iphone".httpGet().apply { close() }
            }.forEach {
                assertEquals(200, it.code())
            }
        }.also { println("$it ms") }
    }

}
