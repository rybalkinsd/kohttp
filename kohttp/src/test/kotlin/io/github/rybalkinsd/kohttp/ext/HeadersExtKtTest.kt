package io.github.rybalkinsd.kohttp.ext

import okhttp3.Headers
import org.junit.Test
import kotlin.test.assertEquals

class HeadersExtKtTest {

    val headers: Headers = Headers.Builder()
            .add("zero", "0")
            .add("one", "1")
            .add("two", "2")
            .add("three", "3")
            .build()

    @Test
    fun `iterating with asSequence`() {
        headers.asSequence().forEachIndexed { index, header ->
            assertEquals(header.value.toInt(), index)
        }
    }

    @Test
    fun `filter and sum`() {
        val sum = headers.asSequence().filter { it.name.contains('o') }
            .sumBy { it.value.toInt() }
        assertEquals(3, sum)
    }

    @Test(expected = NoSuchElementException::class)
    fun `headers does not have 5 elements`() {
        val iterator = headers.asSequence().iterator()
        repeat(5) {
            iterator.next()
        }
    }
}

val Header.name
    get() = this.first

val Header.value
    get() = this.second
