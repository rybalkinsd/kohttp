package io.github.rybalkinsd.kohttp.dsl.context

@HttpDslMarker
class ParamContext {
    private val params: MutableMap<String, MutableList<Any?>> = mutableMapOf()

    infix fun String.to(v: Any?) {
        params.computeIfAbsent(this) { mutableListOf() }.apply {
            when (v) {
                is List<*> -> addAll(v)
                else -> add(v)
            }
        }
    }

    internal fun forEach(action: (k: String, v: Any?) -> Unit) =
        params.forEach { (k, v) ->
            action(k, if (v.size == 1) v.first() else v)
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ParamContext

        if (params != other.params) return false

        return true
    }

    override fun hashCode(): Int {
        return params.hashCode()
    }

}
