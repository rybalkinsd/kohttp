package com.kohttp.ext

import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

internal fun Call.Factory.call(request: Request): Response = newCall(request).execute()

internal suspend fun Call.Factory.suspendCall(request: Request): Response =
    suspendCoroutine { cont ->
        newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                cont.resume(response)
            }

            override fun onFailure(call: Call, e: IOException) {
                cont.resumeWithException(e)
            }
        })
    }

