package io.github.rybalkinsd.kohttp.util

import io.github.rybalkinsd.kohttp.dsl.context.*

@Suppress("UNCHECKED_CAST")
fun <T : HttpContext> createHttpContext(method: Method, init: T.() -> Unit): HttpContext {
    return when (method) {
        Method.GET -> HttpGetContext().apply(init as HttpContext.() -> Unit)
        Method.POST -> HttpPostContext().apply(init as HttpContext.() -> Unit)
        Method.PUT -> HttpPutContext().apply(init as HttpContext.() -> Unit)
        Method.DELETE -> HttpDeleteContext().apply(init as HttpContext.() -> Unit)
        Method.PATCH -> HttpPatchContext().apply(init as HttpContext.() -> Unit)
        Method.HEAD -> HttpHeadContext().apply(init as HttpContext.() -> Unit)
    }
}