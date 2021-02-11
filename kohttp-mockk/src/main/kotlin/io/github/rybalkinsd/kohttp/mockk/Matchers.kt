package io.github.rybalkinsd.kohttp.mockk

import io.github.rybalkinsd.kohttp.dsl.context.HttpContext
import io.github.rybalkinsd.kohttp.dsl.context.HttpGetContext
import io.github.rybalkinsd.kohttp.dsl.context.Method
import io.mockk.MockKMatcherScope
import io.mockk.mockkStatic

/**
 * The only purpose of Matchers class here is to enable a static init block.
 * It'll be possible to clean this API after
 * https://youtrack.jetbrains.com/issue/KT-13486
 */
class Matchers {
    companion object {
        init {
            Method.values()
                    .map { it.name.toLowerCase().capitalize() }
                    .forEach {
                        mockkStatic("io.github.rybalkinsd.kohttp.dsl.Http${it}DslKt")
                    }
        }

        inline fun <reified T : HttpContext> MockKMatcherScope.matching(crossinline init: T.() -> Unit): T.() -> Unit = match {
            with(T::class.java) {
                newInstance().apply(init) == newInstance().apply(it)
            }
        }
    }
}
