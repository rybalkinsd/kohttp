package io.github.rybalkinsd.kohttp.util

import io.github.rybalkinsd.kohttp.dsl.context.*
import io.github.rybalkinsd.kohttp.dsl.context.Method.*

internal fun Method.makeHttpContext(): HttpContext {
    return when (this) {
        GET -> HttpGetContext()
        POST -> HttpPostContext()
        PUT -> HttpPutContext()
        DELETE -> HttpDeleteContext()
        PATCH -> HttpPatchContext()
        HEAD -> HttpHeadContext()
    }
}
