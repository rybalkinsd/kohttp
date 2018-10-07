package com.kohttp.ext

import okhttp3.Handshake
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response


/**
 * This feature is EXPERIMENTAL, API could be changed in the future releases.
 *
 * In many cases response body is simple and it could be treated as String
 *
 *
 * Without `plain()` the correct way to consume response body is
 *      val response = httpGet { }
 *
 *      response.use {
 *          it.body().string()
 *      }
 *
 * However with `plain()` method it is possible to consume easier and get a `PlainResponse` instance
 *      val response = httpGet { }.plain()
 *
 *
 * This method loads entire response body into memory. If the response body is very large this
 * may trigger an {@link OutOfMemoryError}. Prefer to stream the response body if this is a
 * possibility for your response.
 *
 * @since 0.3
 * @author sergey on 8/10/2018
 */
fun Response.plain() = PlainResponse(
        request = request(),
        protocol = protocol(),
        code = code(),
        message = message(),
        handshake = handshake(),
        headers = (0 until headers().size()).map {
            Header(headers().name(it), headers().value(it))
        },
        body = body()?.string(),
        networkResponse = networkResponse(),
        cacheResponse = cacheResponse(),
        priorResponse = priorResponse(),
        sentRequestAtMillis = sentRequestAtMillis(),
        receivedResponseAtMillis = receivedResponseAtMillis()
)

/**
 * PlainResponse is basically the same class as okhttp3.Response
 * except:
 *  - `headers` - they are represented as a list of type `Header`
 *  - `body` - the entire response body in memory represented as `String
 */
data class PlainResponse(
        val request: Request,
        val protocol: Protocol,
        val code: Int,
        val message: String,
        val handshake: Handshake?,
        val headers: List<Header>,
        val body: String?,
        val networkResponse: Response?,
        val cacheResponse: Response?,
        val priorResponse: Response?,
        val sentRequestAtMillis: Long,
        val receivedResponseAtMillis: Long
)

data class Header(val name: String, val value: String)
