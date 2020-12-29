package io.github.rybalkinsd.kohttp.util

import okhttp3.FormBody
import org.junit.Test
import kotlin.test.assertTrue

/**
 * @author sergey
 */
class FormTest {

    @Test
    fun `make form and check toString`() {
        val form = Form().apply {
            "a" to "x"
            "b" to 4
            "c" to 2.1
            " " to " "
            addEncoded("+", "+")
        }.makeBody()

        assertTrue("a" in form)
        assertTrue("b" in form)
        assertTrue("c" in form)

        assertTrue("%20" in form)
        assertTrue(" " !in form)

        assertTrue("+" in form)
        assertTrue("%2B" !in form)

    }

    private operator fun FormBody.contains(k: String) =
        (0 until size).asSequence().any { encodedName(it) == k }

}
