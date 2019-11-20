package io.github.rybalkinsd.kohttp.spring

import io.github.rybalkinsd.kohttp.dsl.httpGet
import io.github.rybalkinsd.kohttp.dsl.httpPost
import okhttp3.Call
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.function.RouterFunction
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.router

/**
 * Configuration based on Spring MVC Router DSL
 *
 * NOTE: available @since Spring 5.2
 */
@Configuration
class Configuration {

    /**
     * Using Router DSL to define MVC controller.
     *
     * NOTE: available @since Spring 5.2
     */
    @Bean
    fun routes(extendedTimeoutClient: Call.Factory): RouterFunction<ServerResponse> = router {
        // maps GET /foobar to httpGet call with defaultHttpClient
        GET("/foobar") {
            val res = httpGet {
                host = "api.github.com"
                path = "/"
            }.also { it.close() }

            ServerResponse.ok().body(res.code())
        }

        // maps POST /foobar to httpPost call with extendedTimeoutClient
        POST("/foobar") {
            // using controller with higher timeout due to potentially slow response time
            val res = httpPost(extendedTimeoutClient) {
                host = "postman-echo.com"
                path = "/post"

                body {
                    json {
                        "login" to "user"
                        "email" to "john.doe@gmail.com"
                    }
                }
            }.also { it.close() }
            ServerResponse.ok().body(res.code())
        }
    }
}
