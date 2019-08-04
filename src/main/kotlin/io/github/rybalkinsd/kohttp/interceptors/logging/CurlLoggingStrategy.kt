package io.github.rybalkinsd.kohttp.interceptors.logging

import io.github.rybalkinsd.kohttp.ext.buildCurlCommand
import okhttp3.Request

/**
 * Logging strategy as curl command format
 *
 * @author doyaaaaaken
 */
class CurlLoggingStrategy : LoggingStrategy {

    override fun log(request: Request, logging: (String) -> Unit) {
        val command = request.buildCurlCommand()
        logging("╭--- cURL command ---")
        logging(command)
        logging("╰--- (copy and paste the above line to a terminal)")
    }
}
