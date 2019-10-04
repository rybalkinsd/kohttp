package io.github.rybalkinsd.kohttp

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ArrayNode
import org.assertj.core.api.Assertions.assertThat

/**
 * todo: Probably it would be better to provide extension function to test Json
 * @author sergey
 */
fun assertContainsExactly(expected: Map<String, Any?>, actual: JsonNode) {
    assertContainsAtLeast(expected, actual)

    actual.fieldNames().forEach {
        assertThat(expected)
                .`as`("Expected does not contain $it")
                .containsKey(it)

        val ex = expected[it]
        if (ex !is List<*>) {
            assertThat(actual[it].asText()).isEqualTo(ex.toString())
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

                assertThat(arrayNode).hasSameSizeAs(v)

                arrayNode.forEachIndexed { i, element ->
                    assertThat(element.asText()).isEqualTo(v[i].toString())
                }

            }
            else -> assertThat(actual[k]?.asText()).isEqualTo(v.toString())
        }

    }
}
