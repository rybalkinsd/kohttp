package io.github.rybalkinsd.kohttp.ext

import okhttp3.Response
import java.io.InputStream

/**
 * Returns Response Body as String.
 *
 * @return Response body as a `String?`.
 * <p>
 * Note: This function will close `ResponseBody`
 * </p>
 * @since 0.9.0
 * @author gokul, sergey
 */
fun Response.asString(): String? = body?.use { it.string() }

/**
 * Returns Response Body as a Stream.
 *
 *
 * @return Response body as a `InputStream?`.
 * <p>
 * Note: Response stream must be closed after use
 * </p>
 * @since 0.9.0
 * @author gokul
 */
fun Response.asStream(): InputStream? = body?.byteStream()
