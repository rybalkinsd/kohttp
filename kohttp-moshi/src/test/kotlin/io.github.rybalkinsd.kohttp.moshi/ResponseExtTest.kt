package io.github.rybalkinsd.kohttp.moshi

import io.mockk.mockk
import okhttp3.MediaType
import okhttp3.Response
import okhttp3.ResponseBody
import org.junit.Test

class ResponseExtTest {
    @Test
    fun test() {
        val response = Response.Builder()
                .request(mockk())
                .protocol(mockk())
                .code(200)
                .message("mock")
                .body(ResponseBody.create(MediaType.get("application/json"), "{\"x\":42}"))
                .build()
        val b = response.toType<B>()
       print(b?.x)
    }
}