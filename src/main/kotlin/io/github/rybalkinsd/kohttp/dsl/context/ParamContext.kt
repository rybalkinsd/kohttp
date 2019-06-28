package io.github.rybalkinsd.kohttp.dsl.context

@HttpDslMarker
class ParamContext {
    private val params: MutableMap<String, MutableList<Any?>> = mutableMapOf()

    infix fun String.to(v: Any?) {
        params.computeIfAbsent(this) { mutableListOf() }.apply {
            when (v) {
                null -> add(null)
                is List<*> -> addAll(v)
                else -> add(v)
            }
        }
    }

    internal fun forEach(action: (k: String, v: Any?) -> Unit) =
        params.forEach { (k, v) ->
            action(k, if (v.size == 1) v.first() else v)
        }

}
