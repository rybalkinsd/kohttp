package io.github.rybalkinsd.kohttp.util

import io.github.rybalkinsd.kohttp.dsl.context.*

@Suppress("UNCHECKED_CAST")
internal fun <T : HttpContext> Method.createHttpContext(): T {
    return when (this) {
        Method.GET -> HttpGetContext()
        Method.POST -> HttpPostContext()
        Method.PUT -> HttpPutContext()
        Method.DELETE -> HttpDeleteContext()
        Method.PATCH -> HttpPatchContext()
        Method.HEAD -> HttpHeadContext()
    } as T
}