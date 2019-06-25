package io.github.rybalkinsd.kohttp.dsl.context

@HttpDslMarker
class ParamContext {
    private val list: MutableList<Pair<String, Any>> = mutableListOf()

    infix fun String.to(v: Any) {
        list.add(Pair(this, v))
    }

    internal fun forEach(action: (k: String, v: Any) -> Unit) =
        list.forEach { (k, v) -> action(k, v) }
}
