package io.github.rybalkinsd.kohttp.interceptors

import io.github.rybalkinsd.kohttp.ext.asSequence
import io.github.rybalkinsd.kohttp.ext.buildCurlCommand
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okio.Buffer

/**
 * Request Logging Interceptor
 *
 * Logs HTTP requests.
 *
 * @param format log format type.
 *   HTTP: http request format
 *   CURL: curl command format
 * @param log function to consume log message
 *
 * Sample Output: [2019-01-28T04:17:42.885Z] GET 200 - 1743ms https://postman-echo.com/get
 *
 * @since 0.8.0
 * @author gokul
 */
class LoggingInterceptor(
        private val format: LoggingFormat = LoggingFormat.HTTP,
        private val log: (String) -> Unit = ::println
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val startTime = System.currentTimeMillis()
        return chain.proceed(request).also { response ->
            log("${request.method()} ${response.code()} - ${System.currentTimeMillis() - startTime}ms ${request.url()}")
            when (format) {
                LoggingFormat.HTTP -> logAsHttp(request)
                LoggingFormat.CURL -> logAsCurl(request)
            }
        }
    }

    private fun logAsHttp(request: Request) {
        //TODO: output http request format log.
        // see https://github.com/rybalkinsd/kohttp/pull/141#issuecomment-516428314
        log("╭--- http request output ---")
        request.headers().asSequence().forEach { log("${it.name}: ${it.value}") }
        Buffer().use {
            request.body()?.writeTo(it)
            log(it.readByteString().utf8())
        }
        log("╰---------------------------")
    }

    private fun logAsCurl(request: Request) {
        val command = request.buildCurlCommand()
        log("╭--- cURL command ---")
        log(command)
        log("╰--- (copy and paste the above line to a terminal)")
    }
}

enum class LoggingFormat {
    HTTP, CURL
}
