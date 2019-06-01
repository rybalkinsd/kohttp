package io.github.rybalkinsd.kohttp

import com.fasterxml.jackson.databind.JsonNode
import io.github.rybalkinsd.kohttp.dsl.async.httpGetAsync
import io.github.rybalkinsd.kohttp.ext.url
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import okhttp3.Response
import kotlin.test.assertEquals

/**
 * @author gokul
 */
fun assertResponses(expected: Map<String, String>, actual: JsonNode) {
    expected.forEach { (t, u) ->
        assertEquals(u, actual[t]?.asText())
    }
}

fun getSuccessfulResponsesAmount(
    results: List<Deferred<Response>>
): Int = runBlocking {
    results.map { getCode(it) }
        .filter { it == 200 }
        .size
}

suspend fun getCode(
    result: Deferred<Response>
): Int =
    try {
        result.await().use {
            it.code()
        }
    } catch (_: Throwable) {
        -1
    }
