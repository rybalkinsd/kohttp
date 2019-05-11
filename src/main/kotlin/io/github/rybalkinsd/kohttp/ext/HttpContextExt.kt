package io.github.rybalkinsd.kohttp.ext

import io.github.rybalkinsd.kohttp.dsl.context.HttpContext
import io.github.rybalkinsd.kohttp.dsl.context.ParamContext
import java.net.URL

/**
 * @since 0.8.0
 * @author sergey
 */
fun HttpContext.url(url: URL) {
    if (url.protocol != "http" && url.protocol != "https") throw IllegalArgumentException("unexpected scheme: $scheme")
    scheme = url.protocol

    host = url.host ?: throw IllegalArgumentException("unexpected host: $host")

    if (url.port != -1) { port = url.port }
    path = url.path
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
    url(URL(url))
}