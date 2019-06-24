package io.github.rybalkinsd.kohttp.util

import org.junit.Test
import kotlin.test.assertEquals

/**
 * @author sergey
 */
class JsonKtTest {

    @Test
    fun `string one field json`() {
        val json = json {
            "a" to "1"
        }
        assertEquals("""{"a":"1"}""", json)
    }

    @Test
    fun `nullable string one field json`() {
        val nString: String? = null
        val json = json {
            "a" to nString
        }
        assertEquals("""{"a":null}""", json)
    }

    @Test
    fun `two fields json`() {
        val json = json {
            "a" to "1"
            "b" to 2
        }
        assertEquals("""{"a":"1","b":2}""", json)
    }

    @Test
    fun `nullable number json`() {
        val nNumber: Number? = null
        val json = json {
            "a" to nNumber
        }
        assertEquals("""{"a":null}""", json)
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
    fun `json with list of nullable Number`() {
        val nNumber: Number? = null
        val json = json {
            "a" to listOf(1, 2f, nNumber, 3L)
            "b" to 2
        }
        assertEquals("""{"a":[1,2.0,null,3],"b":2}""", json)
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
    fun `json with list of nullable String`() {
        val nString: String? = null
        val json = json {
            "a" to listOf("x1", nString, "x3")
        }
        assertEquals("""{"a":["x1",null,"x3"]}""", json)
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
    fun `json with inner nullable json`() {
        val nJson: Json? = null
        val json = json {
            "a" to nJson
        }
        assertEquals("""{"a":null}""", json)
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

    @Test
    fun `json with inner list of nullable any`() {
        val json = json {
            "a" to json {
                "i" to listOf("x", 42, 2.0, null, "any")
            }
        }
        assertEquals("""{"a":{"i":["x",42,2.0,null,"any"]}}""", json)
    }

    @Test
    fun `json with boolean`() {
        val json = json {
            "a" to true
        }
        assertEquals("""{"a":true}""", json)
    }

    @Test
    fun `json with nullable boolean`() {
        val nBoolean: Boolean? = null
        val json = json {
            "a" to nBoolean
        }
        assertEquals("""{"a":null}""", json)
    }

}