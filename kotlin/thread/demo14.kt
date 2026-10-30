package thread

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

// coroutineScope: 创建一个协程作用域，所有在该作用域中启动的协程都会在作用域结束前完成
suspend fun testThread() = coroutineScope {
    launch {
        delay(2.seconds)
        println("Running down in first coroutine")
    }

    launch {
        delay(1.seconds)
        println("Running down in second coroutine")
    }

    println(async {
        delay(0.8.seconds)
        "Result from async"
    }.await())
    println("Running ....")
}

fun main() {
    // runBlocking: 阻塞等待
    kotlinx.coroutines.runBlocking {
        testThread()
        println("Finished testThread")
    }
}