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
 * Created by Sergey on 21/07/2018.
 */
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

fun String.asyncHttpGet(): Deferred<Response> = async {
    asyncHttpGet(this@asyncHttpGet)
}