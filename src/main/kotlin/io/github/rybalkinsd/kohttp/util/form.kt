package io.github.rybalkinsd.kohttp.util

/**
 *
 * @since 0.1.0
 * @author sergey
 */
class Form {
    private val content: MutableList<Pair<String, Any?>> = mutableListOf()

    infix fun String.to(v: Any) {
        content += Pair(this, v)
    }

    override fun toString() = content.joinToString(separator = "&") { "${it.first}=${it.second}" }
}
