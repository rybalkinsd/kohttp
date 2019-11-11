package io.github.rybalkinsd.kohttp.spring_kohttp

import io.github.rybalkinsd.kohttp.dsl.httpGet
import io.github.rybalkinsd.kohttp.dsl.httpPost
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.stereotype.Controller
import org.springframework.web.servlet.function.RouterFunction
import org.springframework.web.servlet.function.RouterFunctions.route
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.ServerResponse.ok
import java.io.IOException
import javax.servlet.ServletException


@Controller
class WebmvcController {
    @Bean
    fun routes(handler: Handler): RouterFunction<ServerResponse> {
        return route()
                .GET("/foobar", handler::handleGet)
                .POST("/foobar", handler::handlePost)
                .build()
    }
}

@Component
class Handler {
    fun handleGet(serverRequest: ServerRequest): ServerResponse {
        val res = httpGet {
            host = "api.github.com"
            path = "/"
        }

        return ok().body(res.code())
    }

    @Throws(ServletException::class, IOException::class)
    fun handlePost(r: ServerRequest): ServerResponse {
        val res = httpPost {
            host = "postman-echo.com"
            path = "/post"

            body {
                json {
                    "login" to "user"
                    "email" to "john.doe@gmail.com"
                }
            }
        }
        return ok().body(res.code())
    }
}
