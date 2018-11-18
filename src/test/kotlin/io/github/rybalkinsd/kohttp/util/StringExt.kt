package io.github.rybalkinsd.kohttp.util

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper

internal val stringMapper = ObjectMapper()

fun String?.asJson() : JsonNode =
    if(isNullOrBlank()) stringMapper.readTree("{}") else stringMapper.readTree(this)
