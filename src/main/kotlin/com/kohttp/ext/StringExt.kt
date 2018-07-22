package com.kohttp.ext

import com.kohttp.client.CommonHttpClient
import okhttp3.Request
import okhttp3.Response

/**
 * Created by sergey on 21/07/2018.
 */
fun String.httpGet(): Response? {
    val request = Request.Builder()
            .url(this)
            .build()
    return CommonHttpClient.newCall(request).execute()
}
