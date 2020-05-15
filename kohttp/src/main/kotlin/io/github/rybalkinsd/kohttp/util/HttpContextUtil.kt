package io.github.rybalkinsd.kohttp.util

import io.github.rybalkinsd.kohttp.dsl.context.*

private val httpContextContainer: Map<Class<out HttpContext>, HttpContext> = mapOf(
        HttpGetContext::class.java to HttpGetContext(),
        HttpPostContext::class.java to HttpPostContext(),
        HttpPutContext::class.java to HttpPutContext(),
        HttpDeleteContext::class.java to HttpDeleteContext(),
        HttpPatchContext::class.java to HttpPatchContext(),
        HttpHeadContext::class.java to HttpHeadContext()
)

@Suppress("UNCHECKED_CAST")
fun <T : HttpContext> translateHttpContext(method: Method?, init: T.() -> Unit): HttpContext {
    return method?.let { createHttpContextFromHttpMethod(it) }
            ?.apply(init as HttpContext.() -> Unit) ?: createHttpContextFromGenericHttpContext(init)
            ?.apply(init as HttpContext.() -> Unit) ?: throw IllegalArgumentException("TODO")
}

private fun createHttpContextFromHttpMethod(method: Method) : HttpContext {
    return method.let {
        when (it) {
            Method.GET -> HttpGetContext()
            Method.POST -> HttpPostContext()
            Method.PUT -> HttpPutContext()
            Method.DELETE -> HttpDeleteContext()
            Method.PATCH -> HttpPatchContext()
            Method.HEAD -> HttpHeadContext()
        }
    }
}

private fun <T : HttpContext> createHttpContextFromGenericHttpContext(init: T.() -> Unit): HttpContext? {
    return init.javaClass.declaredMethods
            .map { it.parameters }
            .flatMap { arrayOf(it).flatten() }
            .map { it.type }
            .mapNotNull { httpContextContainer[it] }
            .firstOrNull()
}