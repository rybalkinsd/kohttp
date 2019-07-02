package io.github.rybalkinsd.kohttp.jvm

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse


fun test() {
    val client = HttpClient.newHttpClient()

    val response = client.send(
        HttpRequest.newBuilder(URI("http://postman-echo.com/get")).build(),
        HttpResponse.BodyHandlers.ofString()
    )

    println(response)
}
