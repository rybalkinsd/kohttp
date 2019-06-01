package io.github.rybalkinsd.kohttp.pool

import kotlinx.coroutines.*
import org.jetbrains.spek.meta.Experimental
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.ForkJoinPool
import kotlin.coroutines.CoroutineContext

/**
 * @since 0.10.0
 * @author evgeny
 */
@Experimental
class CoroutineExecutorService(
        private val executorService: ExecutorService = ForkJoinPool()
) : ExecutorService by executorService,
        ExecutorCoroutineDispatcher() {
    override val executor: Executor
        get() = this

    override fun close() {
        throw UnsupportedOperationException("${CoroutineExecutorService::class.java.name} can not be closed")
    }

    override fun dispatch(context: CoroutineContext, block: Runnable) {
//        println("Dispatch request with context ${context::class.java.name}")
        executorService.execute(block)
    }
}

fun main() {
    val context = CoroutineExecutorService()
    val task = GlobalScope.async(context = context) {
        println(Thread.currentThread().name)
        withContext(context = context) {
            println(Thread.currentThread().name)
        }
    }
    runBlocking {
        task.await()
    }
}
