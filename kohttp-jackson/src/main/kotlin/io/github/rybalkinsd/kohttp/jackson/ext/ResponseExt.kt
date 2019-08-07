package io.github.rybalkinsd.kohttp.jackson.ext

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.NullNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import okhttp3.Response


val stringMapper: ObjectMapper by lazy { jacksonObjectMapper() }


/**
 * Returns Response Body deserialized as [JsonNode] for valid content.
 * Returns [NullNode] if body has no content to bind
 *
 * @return [JsonNode]
 * @throws [JsonParseException] if underlying input contains invalid content
 *    of type {@link JsonParser} supports (JSON for default case)
 * @throws [IOException] If a low-level I/O problem (missing input, network error) occurs
 *
 * @since 0.9.0
 * @author gokul
 */
fun Response.asJson(): JsonNode = with(body()?.string()) {
    stringMapper.readTree(this)
}


/**
 * Returns Response Body as [JsonNode?].
 * Returns null on any exception (I/O, parsing, ...)
 *
 * @return [JsonNode?]
 *
 * @since TODO()
 * @author sergey
 */
fun Response.asJsonOrNull(): JsonNode? = try {
    with(body()?.string()) {
        val parsed = stringMapper.readTree(this)
        if (parsed.isNull) null
        else parsed
    }
} catch (ignored: Exception) {
    null
}


/**
 * Returns deserialized response body as [T]
 *
 * @return [T]
 * @throws [IOException] If a low-level I/O problem (missing input, network error) occurs
 * @throws [JsonParseException] if underlying input contains invalid content
 *    of type {@link JsonParser} supports (JSON for default case)
 * @throws [JsonMappingException] if the input JSON structure does not match structure
 *   expected for result type (or has other mismatch issues)
 *
 * @since TODO()
 * @author sergey
 */
inline fun <reified T : Any> Response.asType(): T? = with(body()?.string()) {
    if (isNullOrBlank()) null
    else stringMapper.readValue<T>(this!!)
}
