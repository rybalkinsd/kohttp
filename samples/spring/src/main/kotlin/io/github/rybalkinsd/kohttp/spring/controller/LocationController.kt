package io.github.rybalkinsd.kohttp.spring.controller

import io.github.rybalkinsd.kohttp.spring.repo.LocationRepo
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

/**
 * [LocationController] accept
 * GET /location  - request to access current location
 * POST /location - request to modify current location
 *
 * @author Sergey
 */
@RestController
class LocationController(private val locationRepo: LocationRepo) {
    @GetMapping("/location")
    fun getHandler(): String = locationRepo.location

    @PostMapping("/location")
    fun postHandler(location: String) {
        locationRepo.location = location
    }
}
