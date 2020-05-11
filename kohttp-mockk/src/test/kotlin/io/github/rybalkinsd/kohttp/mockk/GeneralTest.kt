package io.github.rybalkinsd.kohttp.mockk

import io.github.rybalkinsd.kohttp.dsl.context.HttpContext
import io.github.rybalkinsd.kohttp.dsl.context.HttpGetContext
import io.github.rybalkinsd.kohttp.dsl.context.HttpPatchContext
import io.github.rybalkinsd.kohttp.dsl.httpGet
import io.github.rybalkinsd.kohttp.ext.url
import io.mockk.*
import okhttp3.Request
import okhttp3.Response
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.function.Consumer
import kotlin.reflect.KFunction
import kotlin.reflect.full.memberProperties
import kotlin.test.assertEquals

@EnableKohttpMock
class GeneralTest {




    fun MockKMatcherScope.matching(init: HttpGetContext.() -> Unit): HttpGetContext.() -> Unit = match {
        HttpGetContext().apply(init) == HttpGetContext().apply(it)
    }


    @Test
    fun name() {

        every { httpGet(init = matching {
            url("https://google.com")
        })

        } returns Response {
            request(mockk())
            protocol(mockk())
            code(200)
            message("")
        }


        val a = httpGet {
            url("https://google.com")
        }
        assertThat(a.code()).isEqualTo(42)
    }


}

    fun HttpGetContext.match(o: HttpGetContext?): Boolean {
        HttpGetContext::class.memberProperties.all {
            it.get(this)
            it.get(o)
        }
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HttpContext

        if (method != other.method) return false
        if (paramContext != other.paramContext) return false
        if (headerContext != other.headerContext) return false
        if (scheme != other.scheme) return false
        if (host != other.host) return false
        if (port != other.port) return false
        if (path != other.path) return false

    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HttpContext

        if (method != other.method) return false
        if (paramContext != other.paramContext) return false
        if (headerContext != other.headerContext) return false
        if (scheme != other.scheme) return false
        if (host != other.host) return false
        if (port != other.port) return false
        if (path != other.path) return false

        return true
    }

}