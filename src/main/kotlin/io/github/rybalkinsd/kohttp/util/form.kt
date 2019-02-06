package io.github.rybalkinsd.kohttp.util

import okhttp3.FormBody

/**
 *
 * @since 0.1.0
 * @author sergey
 */
class Form {
    private val bodyBuilder = FormBody.Builder()

    infix fun String.to(v: String) {
        bodyBuilder.add(this, v)
    }

    fun addEncoded(k: String, v: String) {
        bodyBuilder.addEncoded(k, v)
    }

    fun makeBody(): FormBody = bodyBuilder.build()
}
