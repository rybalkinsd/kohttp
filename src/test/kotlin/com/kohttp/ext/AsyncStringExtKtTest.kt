package com.kohttp.ext

import kotlinx.coroutines.experimental.runBlocking
import org.junit.Test
import kotlin.system.measureTimeMillis

/**
 * Created by sergey on 21/07/2018.
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
                            println(it.code())
                        }
            }
            runBlocking {
                "https://www.yandex.ru/search/?text=iphone".asyncHttpGet()

            }
        }.also { println("$it ms") }
    }

}
