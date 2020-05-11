package io.github.rybalkinsd.kohttp.mockk


fun Response(init: okhttp3.Response.Builder.() -> Unit): okhttp3.Response {
    return okhttp3.Response.Builder().also(init).build()
}