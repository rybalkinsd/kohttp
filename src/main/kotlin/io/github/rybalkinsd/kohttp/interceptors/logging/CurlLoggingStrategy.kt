package io.github.rybalkinsd.kohttp.interceptors.logging

import io.github.rybalkinsd.kohttp.ext.buildCurlCommand
import okhttp3.Request

/**
 * Logging strategy as curl command format
 *
 * @author doyaaaaaken
 */
class CurlLoggingStrategy(
        private val logging: (String) -> Unit
) : LoggingStrategy {

    override fun log(request: Request) {
        val command = request.buildCurlCommand()
        logging("╭--- cURL command ---")
        logging(command)
        logging("╰--- (copy and paste the above line to a terminal)")
    }
}
