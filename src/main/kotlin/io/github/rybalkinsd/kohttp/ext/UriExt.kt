package io.github.rybalkinsd.kohttp.ext

import okhttp3.Response
import java.net.URI
import java.net.URL

/**
 * @since 0.8.0
 * @author sergey
 */
fun URI.upload(destination: URL): Response = io.github.rybalkinsd.kohttp.dsl.upload {
    url(destination)
    file(this@upload)
}

/**
 * @since 0.8.0
 * @author sergey
 */
fun URI.upload(destination: String): Response = io.github.rybalkinsd.kohttp.dsl.upload {
    url(destination)
    file(this@upload)
}
