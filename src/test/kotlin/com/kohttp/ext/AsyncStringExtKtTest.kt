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
                    "https://www.yandex.ru/search/?text=qqq&lr=213".asyncHttpGet()
                }.map { it.await() }
                        .forEach {
                            println(it.code())
                        }
            }
        }.also { println("$it ms") }
    }

}
