package io.github.rybalkinsd.kohttp.ext

import io.github.rybalkinsd.kohttp.dsl.context.HttpContext
import java.net.URL

/**
 * @since 0.8.0
 * @author sergey
 */
fun HttpContext.url(url: URL) {
    url.host?.let { host = it }
    if (url.port != -1) { port = url.port }
    url.path?.let { path = it }
    url.protocol?.let { scheme = it }
}

/**
 *
 * @throws `MalformedURLException` if no protocol is specified, or an
 * unknown protocol is found.
 *
 * @since 0.8.0
 * @author sergey
 */
fun HttpContext.url(url: String) {
    url(java.net.URL(url))
}
