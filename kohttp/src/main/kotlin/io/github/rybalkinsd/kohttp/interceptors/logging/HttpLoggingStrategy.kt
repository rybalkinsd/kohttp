package io.github.rybalkinsd.kohttp.interceptors.logging

import io.github.rybalkinsd.kohttp.ext.asSequence
import okhttp3.Request
import okio.Buffer

/**
 * Logging strategy as http request format
 *
 * @author doyaaaaaken
 */
class HttpLoggingStrategy : LoggingStrategy {

    override fun log(request: Request, logging: (String) -> Unit) {
        //TODO: output http request format logging.
        // see https://github.com/rybalkinsd/kohttp/pull/141#issuecomment-516428314
        logging("--- http request output ---")
        request.headers().asSequence().forEach { (name, value) ->
            logging("$name: $value")
        }

        Buffer().use {
            request.body()?.writeTo(it)
            logging(it.readByteString().utf8())
        }
        logging("---------------------------")
    }
}
