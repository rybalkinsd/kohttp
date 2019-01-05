package io.github.rybalkinsd.kohttp

import com.fasterxml.jackson.databind.JsonNode
import kotlin.test.assertEquals

/**
 * Created by Gokul on 03/01/2019.
 */
fun assertResponses(actual: JsonNode, expected: Map<String, String>) {
    expected.forEach { t, u ->
        assertEquals(u, actual.get(t).asText(""))
    }
}

