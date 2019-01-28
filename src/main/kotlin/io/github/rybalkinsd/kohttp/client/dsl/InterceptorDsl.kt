package io.github.rybalkinsd.kohttp.client.dsl

import okhttp3.Interceptor

/**
 * Provides a DSL class to define Interceptors for HTTP Client
 *
 * Usage example using the default `InterceptorsDsl`:
 *
 * <pre>
 *  val forkedClient = defaultHttpClient.fork {
 *  interceptors {
 *        +interceptors[1]
 *        +interceptors[1]
 *      }
 *   }
 *
 * </pre>
 *
 * @see io.github.rybalkinsd.kohttp.client.ForkClientBuilder
 * @see io.github.rybalkinsd.kohttp.client.defaultHttpClient
 *
 * @since 0.8.0
 * @author gokul
 */


class InterceptorsDsl(private val interceptors: MutableList<Interceptor> = mutableListOf()) {

    operator fun Interceptor.unaryPlus() {
        interceptors.add(this)
    }

    fun list() = this.interceptors.toList()
}
