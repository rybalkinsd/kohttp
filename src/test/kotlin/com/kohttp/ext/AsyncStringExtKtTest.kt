package com.kohttp.ext

import kotlinx.coroutines.experimental.runBlocking
import org.junit.Test
import kotlin.system.measureTimeMillis
import kotlin.test.assertEquals

/**
 * Created by Sergey on 21/07/2018.
 */
class AsyncStringExtKtTest {

    @Test
    fun `many async invokes of httpGet`() {
        measureTimeMillis {
            runBlocking {
                List(100) {
                    "https://www.yandex.ru/search/?text=iphone".asyncHttpGet()
                }.map { it.await() }
                        .forEach {
                            assertEquals(200, it.code())
                        }
            }
            runBlocking {
                val response = "https://www.yandex.ru/search/?text=iphone".asyncHttpGet()
                assertEquals(200, response.await().code())
            }
        }.also { println("$it ms") }
    }
}
