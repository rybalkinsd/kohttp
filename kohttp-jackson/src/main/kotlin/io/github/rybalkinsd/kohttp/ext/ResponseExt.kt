package io.github.rybalkinsd.kohttp.ext

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.Response

/**
 * Created by Sergey Rybalkin on 2019-07-11.
 */
internal val stringMapper: ObjectMapper by lazy { ObjectMapper() }

fun Response.asJson(): JsonNode = with(body()?.string()) {
    if (isNullOrBlank()) stringMapper.readTree("{}") else stringMapper.readTree(this)
}
