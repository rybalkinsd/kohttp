package io.github.rybalkinsd.kohttp.util

import org.junit.Test
import kotlin.test.assertEquals

/**
 * @author sergey
 */
class JsonKtTest {

    @Test
    fun `string to string one field json`() {
        val json = json {
            "a" to "1"
        }
        assertEquals( """{"a":"1"}""", json)
    }

    @Test
    fun `two fields json`() {
        val json = json {
            "a" to "1"
            "b" to 2
        }
        assertEquals( """{"a":"1","b":2}""", json)
    }

    @Test
    fun `json with list of Number`() {
        val json = json {
            "a" to listOf(1, 2f, 3L)
            "b" to 2
        }
        assertEquals("""{"a":[1,2.0,3],"b":2}""", json)
    }

    @Test
    fun `json with list of String`() {
        val json = json {
            "a" to listOf("x1", "x2", "x3")
            "b" to 2
        }
        assertEquals("""{"a":["x1","x2","x3"],"b":2}""", json)
    }

    @Test
    fun `json with inner json`() {
        val json = json {
            "a" to json {
                "i" to 42
                "ii" to "abc"
                "iii" to listOf("x", "y", "z")
            }
            "b" to 2
        }
        assertEquals("""{"a":{"i":42,"ii":"abc","iii":["x","y","z"]},"b":2}""", json)
    }

    @Test
    fun `json with inner list of any`() {
        val json = json {
            "a" to json {
                "i" to listOf("x", 42, 2.0, "any")
            }
        }
        assertEquals("""{"a":{"i":["x",42,2.0,"any"]}}""", json)
    }

}