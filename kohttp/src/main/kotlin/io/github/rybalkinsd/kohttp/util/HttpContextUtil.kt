package io.github.rybalkinsd.kohttp.util

import io.github.rybalkinsd.kohttp.dsl.context.*

internal fun Method.makeHttpContext(): HttpContext {
    return when (this) {
        Method.GET -> HttpGetContext()
        Method.POST -> HttpPostContext()
        Method.PUT -> HttpPutContext()
        Method.DELETE -> HttpDeleteContext()
        Method.PATCH -> HttpPatchContext()
        Method.HEAD -> HttpHeadContext()
    }
}