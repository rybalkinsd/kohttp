package io.github.rybalkinsd.kohttp.spring.controller

import io.github.rybalkinsd.kohttp.spring.service.LocationRepo
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

/**
 * [LocationController] accept
 * GET request to access current location
 * POST request to modify current location
 *
 * @author Sergey
 */
@RestController
class LocationController(private val locationRepo: LocationRepo) {
    @GetMapping("/location")
    fun getHandler(): String = locationRepo.location

    @PostMapping("/location")
    fun postHandler(location: String): ResponseEntity<Unit> {
        locationRepo.location = location
        return ResponseEntity.ok().build()
    }
}
