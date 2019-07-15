package io.github.rybalkinsd.kohttp.ext

import okhttp3.Response


/**
 * Returns Response Body as String.
 *
 * @return Response body as a `String?`.
 * @since 0.9.0
 * @author gokul
 */
fun Response.asString() = body()?.string()

/**
 *  Returns Response Body as a Stream.
 *
 * @return Response body as a `InputStream?`.
 * @since 0.9.0
 * @author gokul
 */
fun Response.asStream() = body()?.byteStream()
