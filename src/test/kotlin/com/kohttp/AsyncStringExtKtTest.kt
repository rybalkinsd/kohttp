package com.kohttp

import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Test
import kotlin.system.measureTimeMillis

/**
 * Created by Sergey Rybalkin on 21/07/2018.
 */
class AsyncStringExtKtTest {

    @Test
    fun `async httpGet simple test`() {
        measureTimeMillis {
            runBlocking {
                List(100) {
                    async {
                        "https://www.yandex.ru/search/?text=qqq&lr=213".asyncHttpGet()
                    }
                }.map { it.await() }
                        .forEach {
                            println(it.code())
                        }
            }
        }.also { println("$it ms") }
    }

}