package io.github.rybalkinsd.kohttp.mockk


/**
 * It's an open question how `Response { }` should look like.
 */
fun Response(init: okhttp3.Response.Builder.() -> Unit): okhttp3.Response {
    return okhttp3.Response.Builder().also(init).build()
}