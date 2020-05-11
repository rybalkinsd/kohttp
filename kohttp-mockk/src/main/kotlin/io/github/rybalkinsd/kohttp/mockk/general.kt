package io.github.rybalkinsd.kohttp.mockk

import io.mockk.mockkStatic

annotation class EnableKohttpMock {
    companion object {
        init {
          mockkStatic("io.github.rybalkinsd.kohttp.dsl.HttpGetDslKt")
        }
    }

}

