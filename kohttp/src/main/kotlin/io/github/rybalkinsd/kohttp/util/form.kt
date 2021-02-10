package io.github.rybalkinsd.kohttp.util

import okhttp3.FormBody

/**
 *
 * @since 0.1.0
 * @author sergey
 */
class Form {
    private val bodyBuilder = FormBody.Builder()

    infix fun String.to(v: Any?) {
        bodyBuilder.add(this, v.toString())
    }

    fun addEncoded(k: String, v: String) {
        bodyBuilder.addEncoded(k, v)
    }

    fun makeBody(): FormBody = bodyBuilder.build()
}
