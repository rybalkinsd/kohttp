package io.github.rybalkinsd.kohttp

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ArrayNode
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * todo: Probably it would be better to provide extension function to test Json
 * @author sergey
 */
fun assertContainsExactly(expected: Map<String, Any?>, actual: JsonNode) {
    assertContainsAtLeast(expected, actual)

    actual.fieldNames().forEach {
        assertTrue(expected.containsKey(it), message = "Expected does not contain $it")
        val ex = expected[it]
        if (ex !is List<*>) {
            assertEquals(ex.toString(), actual[it].asText())
        }
    }
}

/**
 * @author gokul
 */
fun assertContainsAtLeast(expected: Map<String, Any?>, actual: JsonNode) {
    expected.forEach { (k, v) ->
        when (v) {
            is List<*> -> {
                val arrayNode = actual[k] as? ArrayNode ?: throw Exception("$k type is not Array, as expected")

                assertEquals(v.size, arrayNode.size())

                arrayNode.forEachIndexed { i, element ->
                    assertEquals(v[i].toString(), element.asText())
                }

            }
            else -> assertEquals(v.toString(), actual[k]?.asText())
        }

    }
}
