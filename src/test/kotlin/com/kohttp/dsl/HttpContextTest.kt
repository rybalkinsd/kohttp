package com.kohttp.dsl

import org.junit.Test

class HttpContextTest {

    @Test(expected = UnsupportedOperationException::class)
    fun `makeBody should produce exception # GET`() {
        HttpGetContext().makeBody()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun `makeBody should produce exception # HEAD`() {
        HttpHeadContext().makeBody()
    }

}