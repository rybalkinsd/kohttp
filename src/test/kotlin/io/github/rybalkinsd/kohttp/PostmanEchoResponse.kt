package io.github.rybalkinsd.kohttp

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
 * Mapping definition class for response from http://postman-echo.com
 *
 * Usage example
 *  <pre>
 *  val res = jacksonObjectMapper().readValue(bodyString, PostmanEchoResponse::class.java)
 *  </pre>
 *
 * @author doyaaaaaken
 */
@JsonIgnoreProperties(ignoreUnknown=true)
data class PostmanEchoResponse(
    val headers: Map<String, String>
)
