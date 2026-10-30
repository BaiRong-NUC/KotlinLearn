package thread

import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.seconds

fun main() {
    runBlocking {
        var job = launch {
            print("thread cancel")
            delay(1.seconds)
            print("error thread cancel")
        }

        delay(0.5.seconds)
        // job.cancel() // 取消协程任务

        // job.join() // 等待协程完成
        job.cancelAndJoin()
    }
}