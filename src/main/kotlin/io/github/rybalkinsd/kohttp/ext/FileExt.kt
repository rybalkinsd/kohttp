package io.github.rybalkinsd.kohttp.ext

import io.github.rybalkinsd.kohttp.dsl.async.asyncHttpPost
import kotlinx.coroutines.Deferred
import okhttp3.Response
import java.io.File
import java.net.URL

/**
 * @since 0.8.0
 * @author sergey
 */
fun File.upload(destination: URL): Response = io.github.rybalkinsd.kohttp.dsl.upload {
    url(destination)
    file(this@upload)
}

/**
 * @since 0.8.0
 * @author sergey
 */
fun File.upload(destination: String): Response = io.github.rybalkinsd.kohttp.dsl.upload {
    url(destination)
    file(this@upload)
}

/**
 * This feature is EXPERIMENTAL, API could be changed in the future releases.
 *
 * @since 0.8.0
 * @author sergey
 */
fun File.asyncUpload(destination: URL): Deferred<Response> = asyncHttpPost {
    url(destination)

    multipartBody {
        +form("file", this@asyncUpload)
    }
}
