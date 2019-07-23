package io.github.rybalkinsd.kohttp.interceptors

import io.github.rybalkinsd.kohttp.ext.asSequence
import io.github.rybalkinsd.kohttp.ext.buildCurlCommand
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer

/**
 * Request Logging Interceptor
 *
 * Logs HTTP requests.
 *
 * @param log function to consume log message
 * @param outputCurlCommand if true, output curl command of the request
 *
 * Sample Output: [2019-01-28T04:17:42.885Z] GET 200 - 1743ms https://postman-echo.com/get
 *
 * @since 0.8.0
 * @author gokul
 */
class LoggingInterceptor(
        private val outputCurlCommand: Boolean = false,
        private val log: (String) -> Unit = ::println
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (outputCurlCommand) {
            val command = request.buildCurlCommand()
            log("╭--- cURL command -------------------------------")
            log(command)
            log("╰--- (copy and paste the above line to a terminal)")
        }
        val startTime = System.currentTimeMillis()

        return chain.proceed(request).also { response ->
            log("${request.method()} ${response.code()} - ${System.currentTimeMillis() - startTime}ms ${request.url()}")

            request.headers().asSequence().forEach { log("${it.name}: ${it.value}") }

            Buffer().use {
                request.body()?.writeTo(it)
                log(it.readByteString().utf8())
            }
        }
    }
}
