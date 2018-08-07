package com.kohttp.ext

import com.kohttp.client.CommonHttpClient
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import kotlin.coroutines.experimental.suspendCoroutine

/**
 * Async version of http GET request with the provided `String` url.
 *
 * @return a `Response` instance
 *
 * Instances of this class are not immutable: the response body is a one-shot
 * value that may be consumed only once and then closed. All other properties are immutable.
 *
 * Usage example:
 * val response = "http://host:port/path/?a=b".asyncHttpGet()
 * ...
 * response.await().use {
 *    your code here
 * }
 *
 * <p>Response This class implements {@link Closeable}. Closing it simply closes its response body. See
 * {@link ResponseBody} for an explanation and examples.
 *
 * @author sergey on 21/07/2018
 */
fun String.asyncHttpGet(): Deferred<Response> = async {
    asyncHttpGet(this@asyncHttpGet)
}

private suspend fun asyncHttpGet(url: String): Response {
    val request = Request.Builder()
            .url(url)
            .build()

    return suspendCoroutine { cont ->
        CommonHttpClient.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                cont.resume(response)
            }

            override fun onFailure(call: Call, e: IOException) {
                cont.resumeWithException(e)
            }
        })
    }
}
