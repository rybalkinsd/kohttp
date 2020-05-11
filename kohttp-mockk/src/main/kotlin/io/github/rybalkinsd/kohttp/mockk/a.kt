package io.github.rybalkinsd.kohttp.mockk

import kotlinx.coroutines.delay

class a {
    var j = 0
    suspend fun  foo() {
        for (i in 10L..10000)
            delay(i)
    }


    fun bar() {
        for (i in 10 until 10000) {
            j++
        }
    }

    fun baz() {
        var i = 10
        while (i++ < 10000) {
            j++
        }
    }
}