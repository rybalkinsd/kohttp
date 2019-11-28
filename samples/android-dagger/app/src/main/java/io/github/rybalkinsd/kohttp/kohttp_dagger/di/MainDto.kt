package io.github.rybalkinsd.kohttp.kohttp_dagger.di

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MainDto(
    val temp: Double = 0.0
)