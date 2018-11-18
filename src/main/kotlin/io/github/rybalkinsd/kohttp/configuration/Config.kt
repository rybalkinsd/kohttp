package io.github.rybalkinsd.kohttp.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.util.concurrent.TimeUnit


/**
 * Created by Sergey on 18/08/2018.
 */
data class Config(
    val client: Client = Client()
) {
    companion object {
        val instance = run {
            Config::class.java.getResource("/kohttp.yaml")?.let {
                val mapper = ObjectMapper(YAMLFactory()).registerKotlinModule()
                mapper.readValue<Config>(it)
            } ?: Config()
        }
    }

    data class Client(
            val connectTimeout: Long = TimeUnit.SECONDS.toMillis(10),
            val readTimeout: Long = TimeUnit.SECONDS.toMillis(10),
            val writeTimeout: Long = TimeUnit.SECONDS.toMillis(10),
            val connectionPool: ConnectionPool = ConnectionPool(),
            val followRedirects: Boolean = true,
            val followSslRedirects: Boolean = true
    ) {

        data class ConnectionPool(
                val maxIdleConnections: Int = 5,
                val keepAliveDuration: Long = TimeUnit.MINUTES.toMillis(5)
        )
    }
}

