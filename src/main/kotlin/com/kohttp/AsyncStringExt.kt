package com.kohttp

import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import kotlin.coroutines.experimental.suspendCoroutine

/**
 * Created by Sergey Rybalkin on 21/07/2018.
 */
suspend fun String.asyncHttpGet(): Response {
    val request = Request.Builder()
            .url(this)
            .build()

    return suspendCoroutine { cont -> CommonHttpClient.newCall(request).enqueue(object : Callback {
        override fun onResponse(call: Call, response: Response) {
            cont.resume(response)
        }

        override fun onFailure(call: Call, e: IOException) {
            cont.resumeWithException(e)
        }
    })


    }
}