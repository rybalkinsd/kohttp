package io.github.rybalkinsd.kohttp.ext

import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import kotlin.system.measureTimeMillis

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
                    assertThat(it.code()).isEqualTo(200)
                }
            }
        }.also { println("$it ms") }
    }
}
