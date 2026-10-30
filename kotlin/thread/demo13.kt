package thread

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.seconds
import kotlin.time.Duration.Companion.milliseconds

private suspend fun fetchUser(): String {
    delay(1.seconds) // 模拟网络请求。
    return "Alice"
}

private suspend fun fetchOrders(): List<String> {
    delay(1.seconds) // 模拟另一个独立的网络请求。
    return listOf("Book", "Pen")
}

private suspend fun calculateTotal(): Int = coroutineScope {
    val productPrice = async {
        delay(500.milliseconds)
        100
    }
    val shippingFee = async {
        delay(500.milliseconds)
        10
    }
    productPrice.await() + shippingFee.await()
}

fun main() = runBlocking {
    // 示例 1：启动任务，执行其他操作，然后获取结果。
    val data = async {
        delay(1.seconds)
        "Data fetched"
    }
    println("Fetching data...")
    println(data.await())

    // 示例 2：并发获取互不依赖的信息。
    val user = async { fetchUser() }
    val orders = async { fetchOrders() }
    println("${user.await()}: ${orders.await()}")

    // 示例 3：在挂起函数中汇总两个独立的结果。
    println("Total price: ${calculateTotal()}")

    // async:需要拿到返回值时使用，返回的是Deferred对象，需要调用await()获取结果. 常见多个请求并发,等所有结果返回后再处理。
}
