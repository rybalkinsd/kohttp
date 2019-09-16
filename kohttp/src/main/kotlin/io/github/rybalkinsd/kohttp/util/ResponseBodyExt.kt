package io.github.rybalkinsd.kohttp.util

import okhttp3.RequestBody
import okio.Buffer

/**
 * Created by Sergey Rybalkin on 2019-09-11.
 */
internal inline fun <reified T> RequestBody.flatMap(transform: (String) -> T): T = transform(
    Buffer().use {
        writeTo(it)
        it.readUtf8()
    }
)
