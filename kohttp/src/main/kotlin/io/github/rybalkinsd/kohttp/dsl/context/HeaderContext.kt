package io.github.rybalkinsd.kohttp.dsl.context

/**
 *
 * @since 0.1.0
 * @author sergey
 */
@HttpDslMarker
class HeaderContext {
    private val map: MutableMap<String, Any> = mutableMapOf()

    infix fun String.to(v: Any) {
        map[this] = v
    }

    fun cookie(init: CookieContext.() -> Unit) {
        map["cookie"] = CookieContext().also(init).collect()
    }

    internal fun forEach(action: (k: String, v: Any) -> Unit) = map.forEach { (k, v) -> action(k, v) }
}
