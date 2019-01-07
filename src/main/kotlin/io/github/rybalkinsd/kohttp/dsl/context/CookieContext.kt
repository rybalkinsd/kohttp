package io.github.rybalkinsd.kohttp.dsl.context

/**
 * @since 0.1.0
 * @author sergey
 */
@HttpDslMarker
class CookieContext {
    private val cookies: MutableList<Pair<String, Any>> = mutableListOf()

    infix fun String.to(v: Any) {
        cookies += Pair(this, v)
    }

    internal fun collect(): String = cookies.joinToString(separator = "; ") { (k, v) -> "$k=$v"}
}
