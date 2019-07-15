package io.github.rybalkinsd.kohttp.ext

import okhttp3.Handshake
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import java.io.InputStream


/**
 * This feature is EXPERIMENTAL, API could be changed in the future releases.
 *
 * In many cases response body is simple and it could be treated as String
 *
 *
 * Without `eager()` the correct way to consume response body is
 *      val response = httpGet { }
 *
 *      response.use {
 *          it.body().string()
 *      }
 *
 * However with `eager()` method it is possible to consume easier and get a `EagerResponse` instance
 *      val response = httpGet { }.eager()
 *
 *
 * This method loads entire response body into memory. If the response body is very large this
 * may trigger an {@link OutOfMemoryError}. Prefer to stream the response body if this is a
 * possibility for your response.
 *
 * @since 0.3.0
 * @author sergey
 */
fun Response.eager() = EagerResponse(
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
 * EagerResponse is basically the same class as okhttp3.Response
 * except:
 *  - `headers` - they are represented as a list of type `Header`
 *  - `body` - the entire response body in memory represented as `String
 *
 * @since 0.3.0
 * @author sergey
 */
data class EagerResponse(
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

/**
 * @since 0.3.0
 * @author sergey
 */
data class Header(val name: String, val value: String)

/**
 * Returns Response Body as String.
 *
 * @return Response body as a `String?`.
 * !! this function will close `ResponseBody`
 * @since 0.9.0
 * @author gokul
 */
fun Response.asString(): String? = body()?.use { it.string() }

/**
 * Returns Response Body as a Stream.
 *
 *
 * @return Response body as a `InputStream?`.
 * !! response stream must be closed after use !!
 * @since 0.9.0
 * @author gokul
 */
fun Response.asStream(): InputStream? = body()?.byteStream()
