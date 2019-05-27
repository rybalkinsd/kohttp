package io.github.rybalkinsd.kohttp.ext

import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.system.measureTimeMillis
import kotlin.test.assertEquals

/**
 * @author sergey
 */
class StringAsyncExtTest {

    @Test
    fun `many async invokes of httpGet`() {
        measureTimeMillis {
            runBlocking {
                val tasks = List(100) {
                    "https://www.yandex.ru/search/?text=iphone".httpGetAsync()
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
