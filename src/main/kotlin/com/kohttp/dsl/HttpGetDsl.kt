package com.kohttp.dsl

import com.kohttp.client.CommonHttpClient
import okhttp3.Response

/**
 * Created by Sergey on 22/07/2018.
 */
fun httpGet(init: HttpGetContext.() -> Unit): Response? {
    val context = HttpGetContext().apply(init)
    return CommonHttpClient.newCall(context.makeRequest()).execute()
}

class HttpGetContext : HttpContext()
