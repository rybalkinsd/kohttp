package io.github.ivsivak.android_kohttp

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Repository(
    val name: String
)