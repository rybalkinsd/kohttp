package io.github.rybalkinsd.kohttp.ext

import okhttp3.Headers

/**
 * @since 0.8.0
 * @author sergey
 */
fun Headers.asSequence(): Sequence<Header> = Sequence {
    object : Iterator<Header> {
        private var cursor: Int = 0
        override fun hasNext() = cursor < size()

        override fun next(): Header {
            if (!hasNext()) throw NoSuchElementException()
            return Header(name(cursor), value(cursor)).also { cursor++ }
        }
    }
}

typealias Header = Pair<String, String>
