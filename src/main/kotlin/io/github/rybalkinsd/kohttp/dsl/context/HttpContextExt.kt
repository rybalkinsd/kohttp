package io.github.rybalkinsd.kohttp.dsl.context

import java.net.URL

fun HttpContext.url(url: URL) {
    url.host?.let { host = it }
    port = url.port
    url.path?.let { path = it }
    url.protocol?.let { scheme = it }
}