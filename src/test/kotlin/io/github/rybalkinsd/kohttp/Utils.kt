package io.github.rybalkinsd.kohttp

import com.fasterxml.jackson.databind.JsonNode
import kotlin.test.assertEquals

/**
 * @author gokul
 */
fun assertResponses(expected: Map<String, String>, actual: JsonNode) {
    expected.forEach { (t, u) ->
        assertEquals(u, actual[t]?.asText())
    }
}
