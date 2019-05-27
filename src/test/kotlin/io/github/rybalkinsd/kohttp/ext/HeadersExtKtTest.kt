package io.github.rybalkinsd.kohttp.ext

import okhttp3.Headers
import org.junit.Test
import kotlin.test.assertEquals

class HeadersExtKtTest {

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

    companion object {
        val headers = Headers.Builder()
                .add("zero", "0")
                .add("one", "1")
                .add("two", "2")
                .add("three", "3")
                .build()
    }
}
