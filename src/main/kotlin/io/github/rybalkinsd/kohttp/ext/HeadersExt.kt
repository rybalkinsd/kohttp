package io.github.rybalkinsd.kohttp.ext

import okhttp3.Headers

fun Headers.asSequence(): Sequence<Header> = Sequence {
    object : Iterator<Header> {
        private var cursor: Int = 0
        override fun hasNext() = cursor < size()
        override fun next() = Header(name(cursor), value(cursor)).also { cursor++ }
    }
}
