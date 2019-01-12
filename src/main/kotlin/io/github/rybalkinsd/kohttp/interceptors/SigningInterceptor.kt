package io.github.rybalkinsd.kohttp.interceptors

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class SigningInterceptor(private val parameterName: String, private val signer: HttpUrl.() -> String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val userRequest: Request = chain.request()
        val requestBuilder = userRequest.newBuilder()

        val url = userRequest.url()
        val urlBuilder = url.newBuilder()

        val signedKey = signer(url)

        urlBuilder.addQueryParameter(parameterName, signedKey).build()
        requestBuilder.url(urlBuilder.build())

        return chain.proceed(requestBuilder.build())
    }
}
