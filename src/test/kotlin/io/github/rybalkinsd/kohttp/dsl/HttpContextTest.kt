package io.github.rybalkinsd.kohttp.dsl

import io.github.rybalkinsd.kohttp.dsl.context.HttpGetContext
import io.github.rybalkinsd.kohttp.dsl.context.HttpHeadContext
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