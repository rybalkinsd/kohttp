package io.github.rybalkinsd.kohttp.interceptors.logging

import okhttp3.Request

/**
 * Logging strategy
 *
 * @author doyaaaaaken
 */
interface LoggingStrategy {

    fun log(request: Request, logging: (String) -> Unit)
}
