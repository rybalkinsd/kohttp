package io.github.rybalkinsd.kohttp.android_kohttp

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Discover(
    val data: List<Flit> = emptyList()
)

@JsonClass(generateAdapter = true)
data class Flit(
    val userName: String,
    val content: String
)
