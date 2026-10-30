package thread

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.time.Duration.Companion.seconds

suspend fun theadWitch() = coroutineScope {
    launch {
        println("launch thread: ${Thread.currentThread().name}")


        // 切换线程IO线程处理
        withContext(Dispatchers.IO) {
            println("IO thread: ${Thread.currentThread().name}")
            delay(1.seconds)
            println("IO thread Down")
        }

        // 切换回Default线程处理
        withContext(Dispatchers.Default) {
            println("Default thread: ${Thread.currentThread().name}")
            delay(1.seconds)
            println("Default thread Down")
        }
        println("back thread: ${Thread.currentThread().name}")
    }
}

fun main() {
    runBlocking {
        theadWitch()
    }
}
