package io.github.rybalkinsd.kohttp.jackson.ext

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.NullNode
import okhttp3.Response

/**
 * Created by Sergey Rybalkin on 2019-07-11.
 */
internal val stringMapper: ObjectMapper by lazy { ObjectMapper() }



/**
 * Returns Response Body as JSON. If Response is `null` it returns a NullNode
 *
 * @return JsonNode.
 * @since 0.9.0
 * @author gokul
 */
fun Response.asJson(): JsonNode = with(body()?.string()) {
    if (isNullOrBlank()) NullNode.instance else stringMapper.readTree(this)
}
