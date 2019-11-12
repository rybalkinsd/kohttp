package io.github.rybalkinsd.kohttp.spring_kohttp

import io.github.rybalkinsd.kohttp.dsl.httpGet
import io.github.rybalkinsd.kohttp.dsl.httpPost
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Controller {

    @GetMapping("/foo")
    fun getHandler(): Int {
        val res = httpGet {
            host = "api.github.com"
            path = "/"
        }

        return res.code()
    }

    @PostMapping("/foo")
    fun posthandler(): Int {
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
        return res.code()
    }
}
