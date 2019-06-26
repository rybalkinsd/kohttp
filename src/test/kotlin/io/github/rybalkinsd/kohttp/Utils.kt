package io.github.rybalkinsd.kohttp

import com.fasterxml.jackson.databind.JsonNode
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * @author sergey
 */
fun assertContainsExactly(expected: Map<String, Any?>, actual: JsonNode) {
    assertContainsAtLeast(expected, actual)

    actual.fieldNames().forEach {
        assertTrue(expected.containsKey(it), message = "Expected does not contain $it")
        assertEquals(expected[it].toString(), actual[it].asText())
    }
}

/**
 * @author gokul
 */
fun assertContainsAtLeast(expected: Map<String, Any?>, actual: JsonNode) {
    expected.forEach { (t, u) ->
        assertEquals(u.toString(), actual[t]?.asText())
    }
}
