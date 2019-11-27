package io.github.rybalkinsd.kohttp.spring.repo

import org.springframework.stereotype.Repository

/**
 * @author Sergey
 */
interface LocationRepo {
    var location: String
}

/**
 * [LocationRepo] is a primitive Repository to store location.
 *
 */
@Repository
class LocationRepoImpl : LocationRepo {
    override var location: String = "London"
}
