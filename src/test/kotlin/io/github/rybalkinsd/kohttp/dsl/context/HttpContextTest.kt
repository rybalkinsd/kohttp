package io.github.rybalkinsd.kohttp.dsl.context

import org.junit.Test

/**
 * @author sergey
 */
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