package io.github.rybalkinsd.kohttp.util


/**
 *
 * @since 0.1.0
 * @author sergey
 */
fun json(init: Json.() -> Unit): String = Json().also(init).toString()

/**
 *
 * @since 0.1.0
 * @author sergey
 */
class Json {
    private val elements = mutableListOf<Pair<String, String>>()

    fun json(init: Json.() -> Unit) = Json().also(init)

    infix fun String.to(obj: List<*>) {
        val v = obj.joinToString(separator = ",", prefix = "[", postfix = "]") {
            when (it) {
                null -> "null"
                is Number, is Json, is Boolean -> it.toString()
                else -> """"$it""""
            }
        }
        elements += Pair(this, v)
    }

    infix fun String.to(str: String?) {
        elements += if (str == null) Pair(this, "null") else Pair(this, """"$str"""")
    }

    infix fun String.to(num: Number?) {
        elements += Pair(this, num.toString())
    }

    infix fun String.to(json: Json?) {
        elements += Pair(this, json.toString())
    }

    infix fun String.to(bool: Boolean?) {
        elements += Pair(this, bool.toString())
    }

    override fun toString(): String =
        elements.joinToString(separator = ",", prefix = "{", postfix = "}") { (k, v) ->
            """"$k":$v"""
        }
}
