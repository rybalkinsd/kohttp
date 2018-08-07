package com.kohttp.client

import okhttp3.Call
import okhttp3.OkHttpClient

/**
 * Created by Sergey on 21/07/2018.
 */
internal object CommonHttpClient : Call.Factory by OkHttpClient()
