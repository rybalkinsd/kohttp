package io.github.rybalkinsd.kohttp.moshi

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import okhttp3.Response

//
@JsonClass(generateAdapter = true)
class B(val x: Int = 42)

val moshi = Moshi.Builder().build()

inline fun <reified T> Response.toType(): T? =
    with(body()?.string()) {
        if (isNullOrBlank()) null
        else moshi.adapter(T::class.java).fromJson(this!!)
    }
