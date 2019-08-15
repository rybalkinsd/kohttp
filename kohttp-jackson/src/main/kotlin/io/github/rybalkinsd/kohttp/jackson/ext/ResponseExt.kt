package io.github.rybalkinsd.kohttp.jackson.ext

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.NullNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import okhttp3.Response


class DeserializationException(cause: String) : Exception(cause)

val default: ObjectMapper by lazy { jacksonObjectMapper() }


/**
 * Returns Response Body deserialized as [JsonNode] for valid content.
 * Returns [NullNode] if body has no content to bind
 *
 * @return [JsonNode]
 *
 * @throws [DeserializationException] if `RequestBody` is null
 * @throws [JsonParseException] if underlying input contains invalid content
 *    of type {@link JsonParser} supports (JSON for default case)
 * @throws [IOException] If a low-level I/O problem (missing input, network error) occurs
 *
 * @since 0.9.0
 * @author gokul
 */
fun Response.toJson(mapper: ObjectMapper = default): JsonNode {
    val content = body()?.string()

    return when {
        content.isNullOrBlank() -> throw DeserializationException(cause = "Request body is '$content'")
        else -> mapper.readTree(content)
    }
}

/**
 * Returns Response Body as [JsonNode?].
 * Returns null on invalid content
 *
 * @return [JsonNode] and on invalid Json content
 *
 * @throws [IOException] If a low-level I/O problem (missing input, network error) occurs
 *
 * @since 0.11.0
 * @author sergey
 */
fun Response.toJsonOrNull(): JsonNode? = try {
    toJson()
} catch (ignored: DeserializationException) {
    null
} catch (ignored: JsonParseException) {
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
inline fun <reified T : Any> Response.toType(): T? = with(body()?.string()) {
    if (isNullOrBlank()) null
    else default.readValue<T>(this!!)
}
