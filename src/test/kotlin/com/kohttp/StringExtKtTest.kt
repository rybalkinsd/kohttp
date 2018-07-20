package com.kohttp

import kotlinx.coroutines.experimental.runBlocking
import org.junit.Test
import kotlin.system.measureTimeMillis

/**
 * Created by Sergey Rybalkin on 21/07/2018.
 */
class StringExtKtTest {

    @Test
    fun `httpGet simple test`() {
        val result = "https://www.yandex.ru/search/?text=qqq&lr=213".httpGet()
        println(result)
    }

    @Test
    fun `sync invoke many httpGet`() {
        measureTimeMillis {
            runBlocking {
                List(100) {
                    "https://www.yandex.ru/search/?text=qqq&lr=213".asyncHttpGet()
                }.forEach {
                    println(it.code())
                }
            }
        }.also { println("$it ms") }

    }


    @Test
    fun `async httpGet with param`() {
        "www.yandex.ru".httpGet {
            param {
                "text" to "qqq"
                "lr" to 213
            }
        }.also {
            println(it)
        }
    }


}
