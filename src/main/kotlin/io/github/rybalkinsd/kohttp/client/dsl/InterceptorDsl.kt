package io.github.rybalkinsd.kohttp.client.dsl

import okhttp3.Interceptor

/**
 * Provides a DSL class to define Interceptors for HTTP Client
 *
 * Usage example with default `InterceptorsDsl`:
 *
 * <pre>
 *  val forkedClient = defaultHttpClient.fork {
 *  interceptors {
 *        +interceptor1
 *        +interceptor2
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
        interceptors += this
    }

    fun list(): List<Interceptor> = this.interceptors
}
