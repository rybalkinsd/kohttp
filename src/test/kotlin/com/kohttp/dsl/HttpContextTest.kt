package com.kohttp.dsl

import org.junit.Test

class HttpContextTest {

    @Test(expected = UnsupportedOperationException::class)
    fun `make GET request with body`() {
        HttpContext().makeBody()
    }

}