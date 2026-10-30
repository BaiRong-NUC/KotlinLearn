package base

fun listDemo() {
    // 只读列表
    var list = listOf(1, 2, 3)
    println(list.size)

    var carList = mutableListOf("Toyota", "Honda", "Ford")

    carList.add("Chevrolet")

    print(carList)
}

fun mapDemo() {
    var map = mapOf("one" to 1, "two" to 2, "three" to 3)
    println(map["one"])

    for ((key, value) in map) {
        println("$key -> $value")
    }

    var mutableMap = mutableMapOf("one" to 1, "two" to 2, "three" to 3)
    mutableMap["four"] = 4
    println(mutableMap)
}

fun main() {
    base.listDemo()
    base.mapDemo()
}