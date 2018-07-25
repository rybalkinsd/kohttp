package com.kohttp.util

fun json(init: Json.() -> Unit): String = Json().also(init).toString()

class Json {
    private val elements = mutableListOf<Pair<String, String>>()

    fun json(init: Json.() -> Unit) = Json().also(init)

    infix fun String.to(obj: List<*>) {
        val transform: ((Any?) -> CharSequence)? = when {
            obj.all { it is String } -> { { """"$it"""" } }
            else -> null
        }

        val v = obj.joinToString(separator = ",", prefix = "[", postfix = "]", transform = transform)
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
