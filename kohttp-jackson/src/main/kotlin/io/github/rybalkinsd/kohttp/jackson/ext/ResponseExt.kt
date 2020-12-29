package io.github.rybalkinsd.kohttp.jackson.ext

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import okhttp3.Response


/**
 * Deserialize [ResponseBody] as [JsonNode].
 *
 * @param mapper is a [ObjectMapper] that gonna be used to deserialize [JsonNode]
 * @return [JsonNode]
 *
 * @throws [DeserializationException] if there is no content to bind.
 * @throws [JsonParseException] if underlying input contains invalid content
 *    of type {@link JsonParser} supports (JSON for default case)
 * @throws [IOException] If a low-level I/O problem (missing input, network error) occurs
 *
 * @since 0.9.0
 * @author gokul, sergey
 */
fun Response.toJson(mapper: ObjectMapper = default): JsonNode {
    val content = body?.string()

    return when {
        content.isNullOrBlank() -> throw DeserializationException(cause = "Request body is '$content'")
        else -> mapper.readTree(content)
    }
}

/**
 * Deserialize [ResponseBody] as [JsonNode?].
 * Returns null on invalid content
 *
 * @param mapper is a [ObjectMapper] that gonna be used to deserialize [JsonNode]
 * @return [JsonNode] and on invalid Json content
 *
 * @throws [IOException] If a low-level I/O problem (missing input, network error) occurs
 *
 * @since 0.11.0
 * @author sergey
 */
fun Response.toJsonOrNull(mapper: ObjectMapper = default): JsonNode? = try {
    toJson(mapper)
} catch (ignored: DeserializationException) {
    null
} catch (ignored: JsonParseException) {
    null
}

/**
 * Deserialize response body as [T]
 *
 * @param mapper is a [ObjectMapper] that gonna be used to deserialize [JsonNode]
 * @return [T]?
 *
 * @throws [IOException] If a low-level I/O problem (missing input, network error) occurs
 * @throws [JsonParseException] if underlying input contains invalid content
 *    of type {@link JsonParser} supports (JSON for default case)
 * @throws [JsonMappingException] if the input JSON structure does not match structure
 *   expected for result type (or has other mismatch issues)
 *
 * @since 0.11.0
 * @author sergey
 */
inline fun <reified T : Any> Response.toType(mapper: ObjectMapper = default): T? = with(body?.string()) {
    if (isNullOrBlank()) null
    else mapper.readValue<T>(this!!)
}

val default: ObjectMapper by lazy { jacksonObjectMapper() }

class DeserializationException(cause: String) : Exception(cause)
