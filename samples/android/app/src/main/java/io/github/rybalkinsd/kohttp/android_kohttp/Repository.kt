package io.github.rybalkinsd.kohttp.android_kohttp

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Repository(
    val name: String
)