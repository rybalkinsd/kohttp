package com.kohttp.ext

import com.kohttp.client.CommonHttpClient
import okhttp3.Request
import okhttp3.Response

/**
 * This extension performs an GET request with the provided `String` url.
 *
 * @return a `Response` instance
 *
 * Instances of this class are not immutable: the response body is a one-shot
 * value that may be consumed only once and then closed. All other properties are immutable.
 *
 * Usage example:
 * val response = "http://host:port/path/?a=b".httpGet()
 * response.use {
 *    your code here
 * }
 *
 * <p>Response This class implements {@link Closeable}. Closing it simply closes its response body. See
 * {@link ResponseBody} for an explanation and examples.
 *
 * @author sergey on 21/07/2018
 */
fun String.httpGet(): Response {
    val request = Request.Builder()
            .url(this)
            .build()
    return CommonHttpClient.newCall(request).execute()
}
