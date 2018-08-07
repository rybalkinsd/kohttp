package com.kohttp.util

import org.junit.Test
import kotlin.test.assertEquals

class FormTest {

    @Test
    fun `make form and check toString`() {
        val form = Form().apply {
            "a" to "x"
            "b" to 4
            "c" to 2.1
        }

        assertEquals("a=x&b=4&c=2.1", form.toString())
    }
}
