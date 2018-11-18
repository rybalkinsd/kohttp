package io.github.rybalkinsd.kohttp.util

fun json(init: Json.() -> Unit): String = Json().also(init).toString()

class Json {
    private val elements = mutableListOf<Pair<String, String>>()

    fun json(init: Json.() -> Unit) = Json().also(init)

    infix fun String.to(obj: List<*>) {
        val v = obj.joinToString(separator = ",", prefix = "[", postfix = "]") {
            when(it) {
                is Number, is Json -> it.toString()
                else -> """"$it""""
            }
        }
        elements += Pair(this, v)
    }

    infix fun String.to(str: String) {
        elements += Pair(this, """"$str"""")
    }

    infix fun String.to(num: Number) {
        elements += Pair(this, num.toString())
    }

    infix fun String.to(json: Json) {
        elements += Pair(this, json.toString())
    }

    override fun toString(): String =
        elements.joinToString(separator = ",", prefix = "{", postfix = "}") {
            (k,v) -> """"$k":$v"""
        }
}
