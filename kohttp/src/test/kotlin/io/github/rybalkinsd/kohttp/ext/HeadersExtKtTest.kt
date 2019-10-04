package io.github.rybalkinsd.kohttp.ext

import okhttp3.Headers
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class HeadersExtKtTest {

    private val headers: Headers = Headers.Builder()
            .add("zero", "0")
            .add("one", "1")
            .add("two", "2")
            .add("three", "3")
            .build()

    @Test
    fun `iterating with asSequence`() {
        headers.asSequence().forEachIndexed { index, header ->
            assertThat(index).isEqualTo(header.value.toInt())
        }
    }

    @Test
    fun `filter and sum`() {
        val sum = headers.asSequence().filter { it.name.contains('o') }
            .sumBy { it.value.toInt() }
        assertThat(sum).isEqualTo(3)
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
