package thread

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.microseconds

suspend fun fetchData(): String {
    // 模拟耗时操作
    kotlinx.coroutines.delay(1000.microseconds)
    return "Data fetched"
}

fun main() = runBlocking {
    // 启动协程,不等待返回值,常用于UI更新,网络请求等场景
    launch {
        val data = fetchData()
        println(data)
    }

    print("Fetching data...")
}
