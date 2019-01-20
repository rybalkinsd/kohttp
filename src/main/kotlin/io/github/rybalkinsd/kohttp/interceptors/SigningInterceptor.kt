package io.github.rybalkinsd.kohttp.interceptors

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Request Signing Interceptor
 *
 * Enables signing of query parameters.
 *
 * @param parameterName the name of the parameter with signed key
 * @param signer function with HttpUrl as a receiver to sign the request parameter
 *
 * @see HttpUrl
 *
 * @since 0.8.0
 * @author gokul
 */

class SigningInterceptor(private val parameterName: String, private val signer: HttpUrl.() -> String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        return with(chain.request()) {
            val signedKey = signer(url())

            val requestUrl = with(url().newBuilder()) {
                addQueryParameter(parameterName, signedKey)
                build()
            }

            with(newBuilder()) {
                url(requestUrl)
                chain.proceed(build())
            }
        }
    }
}
