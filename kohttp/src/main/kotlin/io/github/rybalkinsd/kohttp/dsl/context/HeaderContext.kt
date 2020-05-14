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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HeaderContext

        if (map != other.map) return false

        return true
    }

    override fun hashCode(): Int {
        return map.hashCode()
    }
}
