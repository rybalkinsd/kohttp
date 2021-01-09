package io.github.rybalkinsd.kohttp.util

import okhttp3.FormBody
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * @author sergey
 */
class FormTest {

    @Test
    fun `make form and check toString`() {
        val nullableParam: String? = "notNull"
        val form = Form().apply {
            "a" to "x"
            "b" to 4
            "c" to 2.1
            "d" to nullableParam
            "e" to null
            " " to " "
            addEncoded("+", "+")
        }.makeBody()
        val namesToValues = form.toMapNamesToValues()

        assertEquals("x", namesToValues["a"])
        assertEquals("4", namesToValues["b"])
        assertEquals("2.1", namesToValues["c"])
        assertEquals("notNull", namesToValues["d"])
        assertEquals("null", namesToValues["e"])
        assertEquals("%20", namesToValues["%20"])
        assertEquals("+", namesToValues["+"])

        assertTrue(!namesToValues.containsKey(" "))
        assertTrue(!namesToValues.containsKey("%2B"))

    }

    private fun FormBody.toMapNamesToValues(): Map<String, String> {
        return (0 until size)
            .map { encodedName(it) to encodedValue(it) }
            .toMap()
    }

}
