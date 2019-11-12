package io.github.rybalkinsd.kohttp.spring_kohttp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringKohttpApplication

fun main(args: Array<String>) {
	runApplication<SpringKohttpApplication>(*args)
}
