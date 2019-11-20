package io.github.rybalkinsd.kohttp.spring

import io.github.rybalkinsd.kohttp.dsl.httpGet
import io.github.rybalkinsd.kohttp.dsl.httpPost
import okhttp3.Call
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Annotation based REST controller definition
 */
@RestController
class Controller {

    @Autowired
    lateinit var extendedTimeoutClient : Call.Factory

    @GetMapping("/foo")
    fun getHandler(): Int {
        val res = httpGet {
            host = "api.github.com"
            path = "/"
        }.also { it.close() }

        return res.code()
    }

    @PostMapping("/foo")
    fun postHandler(): Int {
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

        return res.code()
    }
}
