package io.github.rybalkinsd.kohttp.interceptors.logging

import io.github.rybalkinsd.kohttp.ext.asSequence
import io.github.rybalkinsd.kohttp.util.flatMap
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.RequestBody
import okhttp3.Response
import java.util.*

/**
 * Logs HTTP requests.
 *
 * @param log log consumer
 *
 * --> 706213ee-3a26-4d2d-ab09-f4895f7ce295
 * POST http://postman-echo.com/post?arg=iphone
 * one 42
 * cookie aaa=bbb; ccc=42
 *
 * login=user&email=john.doe%40gmail.com
 * ---
 * <-- 706213ee-3a26-4d2d-ab09-f4895f7ce295
 * 200 http://postman-echo.com/post?arg=iphone
 * Content-Type application/json; charset=utf-8
 * Server nginx
 * Vary Accept-Encoding
 * Connection keep-alive
 * ---
 *
 * @since 0.11.0
 * @author sergey
 */
class HttpLoggingInterceptor(
        private val log: (String) -> Unit = ::println
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val connection = chain.connection()
        val id = UUID.randomUUID()

        log("--> $id")
        log("${request.method} ${request.url} ${connection?.protocol() ?: ""}")
        log(request.headers)
        log("")
        request.body?.let { log(it) }
        log("---")


        val start = System.currentTimeMillis()
        val timing: Long
        val response: Response = try {
            chain.proceed(request).also {
                timing = System.currentTimeMillis() - start
            }
        } catch (e: Exception) {
            log("<-- FAIL: $e")
            throw e
        }

        log("<-- $id in $timing ms")
        log("${response.code} ${response.request.url}")
        log(response.headers)
        log("---")
        return response
    }

    private fun log(headers: Headers) {
        headers.asSequence().forEach { (k, v) ->
            log("$k $v")
        }
    }

    private fun log(body: RequestBody) {
        body.flatMap {
            it.replace("\n", "\\n")
                .replace("\r", "\\r")
        }.also {
            log(it)
        }
    }
}

