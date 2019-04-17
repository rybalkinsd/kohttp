package io.github.rybalkinsd.kohttp.ext

import io.github.rybalkinsd.kohttp.dsl.async.asyncHttpPost
import io.github.rybalkinsd.kohttp.dsl.context.url
import io.github.rybalkinsd.kohttp.dsl.httpPost
import kotlinx.coroutines.Deferred
import okhttp3.Response
import java.io.File
import java.net.URL

fun File.upload(url: URL): Response = httpPost {
    url(url)

    multipartBody {
        +form("file", this@upload)
    }
}

fun File.asyncUpload(url: URL): Deferred<Response> = asyncHttpPost {
    url(url)

    multipartBody {
        +form("file", this@asyncUpload)
    }
}
