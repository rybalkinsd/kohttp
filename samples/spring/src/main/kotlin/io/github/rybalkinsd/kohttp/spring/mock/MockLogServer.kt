package io.github.rybalkinsd.kohttp.spring.mock

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.function.body
import org.springframework.web.servlet.function.router
import kotlin.random.Random

/**
 * It's rather complicated to install any log service with REST API.
 * So, we're using a [MockLogServer] to emulate such a log service.
 *
 * [MockLogServer] accept POST requests with log data, however, it may
 * take a while to respond.
 *
 * [MockLogServer] accept GET requests to provide existing logs.
 *
 * @author Sergey
 */
@Configuration
class MockLogServer {
    val logs = mutableListOf<WeatherLog>()

    @Bean
    fun routs() = router {
        GET("/log/weather") {
            ok().body(logs)
        }
        
        // Emulating that log store operation take a lot of time
        POST("/log/weather") {
            Thread.sleep(Random.nextLong(10_000, 20_000))
            logs += it.body<WeatherLog>()
            ok().build()
        }
    }
}

data class WeatherLog(
    val timestamp: Long,
    val location: String,
    val temperature: Double
)
