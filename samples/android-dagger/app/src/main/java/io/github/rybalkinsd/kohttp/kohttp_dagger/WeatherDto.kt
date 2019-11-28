package io.github.rybalkinsd.kohttp.kohttp_dagger

import com.squareup.moshi.JsonClass
import io.github.rybalkinsd.kohttp.kohttp_dagger.di.MainDto

@JsonClass(generateAdapter = true)
data class WeatherDto(
    val main: MainDto = MainDto()
)
