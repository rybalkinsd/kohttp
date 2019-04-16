package io.github.rybalkinsd.kohttp.dsl

import io.github.rybalkinsd.kohttp.dsl.async.asyncHttpPost
import kotlinx.coroutines.Deferred
import okhttp3.Response
import java.io.File
import java.net.URL

fun File.upload(url: URL): Response = httpPost {
    host = url.host
    port = url.port
    path = url.path
    scheme = url.protocol

    multipartBody {
        +form("file", this@upload)
    }
}

fun File.asyncUpload(url: URL): Deferred<Response> = asyncHttpPost {
    host = url.host
    port = url.port
    path = url.path
    scheme = url.protocol

    multipartBody {
        +form("file", this@asyncUpload)
    }
}
