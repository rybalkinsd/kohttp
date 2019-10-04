package io.github.rybalkinsd.kohttp.dsl.context

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * @author doyaaaaaken
 */
class HttpPostContextTest {

    @Test
    fun `application json content type when content type is set on 'body' method`() {
        val expected = "application/json; charset=utf-8"
        val context = HttpPostContext()
        context.body("application/json") { string("content") }

        assertThat(context.makeBody().contentType().toString()).isEqualTo(expected)
    }

    @Test
    fun `x-www-form-urlencoded content type when body is form`() {
        val expected = "application/x-www-form-urlencoded; charset=utf-8"
        val context = HttpPostContext()
        context.body { form("") }

        assertThat(context.makeBody().contentType().toString()).isEqualTo(expected)
    }

    @Test
    fun `application json content type when body is json`() {
        val expected = "application/json; charset=utf-8"
        val context = HttpPostContext()
        context.body { json {} }

        assertThat(context.makeBody().contentType().toString()).isEqualTo(expected)
    }

    @Test
    fun `content type on 'form' method inside body overrides it on 'body' method`() {
        val expected = "application/x-www-form-urlencoded; charset=utf-8"
        val context = HttpPostContext()
        context.body("application/json; charset=utf-8") { form("") }

        assertThat(context.makeBody().contentType().toString()).isEqualTo(expected)
    }

}
