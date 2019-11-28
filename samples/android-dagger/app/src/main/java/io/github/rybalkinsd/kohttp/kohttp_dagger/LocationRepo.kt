package io.github.rybalkinsd.kohttp.kohttp_dagger

import javax.inject.Inject

interface LocationRepo {
    val location: String
}

class LocationRepoImpl @Inject constructor() : LocationRepo {

    override val location: String = "London"

}
