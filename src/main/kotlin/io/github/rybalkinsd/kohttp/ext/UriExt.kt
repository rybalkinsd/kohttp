package io.github.rybalkinsd.kohttp.ext

import kotlinx.coroutines.Deferred
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

/**
 * @since 0.10.0
 * @author evgeny
 */
fun URI.uploadAsync(destination: URL): Deferred<Response> = io.github.rybalkinsd.kohttp.dsl.async.uploadAsync {
    url(destination)
    file(this@uploadAsync)
}

/**
 * @since 0.10.0
 * @author evgeny
 */
fun URI.uploadAsync(destination: String): Deferred<Response> = io.github.rybalkinsd.kohttp.dsl.async.uploadAsync {
    url(destination)
    file(this@uploadAsync)
}
