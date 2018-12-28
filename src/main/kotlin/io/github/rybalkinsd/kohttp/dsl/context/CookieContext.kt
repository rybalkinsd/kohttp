package io.github.rybalkinsd.kohttp.dsl.context

@HttpDslMarker
class CookieContext {
    private val cookies: MutableList<Pair<String, Any>> = mutableListOf()

    infix fun String.to(v: Any) {
        cookies += Pair(this, v)
    }

    internal fun collect(): String = cookies.joinToString(separator = "; ") { (k, v) -> "$k=$v"}
}