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
                val tasks = List(1000) {
                    "https://www.yandex.ru/search/?text=iphone".asyncHttpGet()
                }
                tasks.map { r ->
                    r.await().also { it.close() }
                }.forEach {
                    assertEquals(200, it.code())

                }
            }
        }.also { println("$it ms") }
    }
}
