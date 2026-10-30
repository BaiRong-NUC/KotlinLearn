package base

fun sum(left: Int, right: Int): Int {
    return left + right
}

fun sum2(left: Int, right: Int) = left + right

fun demo2() {
    // for (i in 1..10) {
    //     println(i)
    // }

    for (i in 1..10 step 2) {
        println(i)
    }

    val array = listOf(1, 2, 3, 4, 5)

    for ((index, item) in array.withIndex()) {
        println("index: $index, item: $item")
    }
}


fun main() {
    // println(sum(3, 4))
    // var name = "张三"
    // var age = 18
    // println("姓名:${name}，年龄:${age * 2}")
    // var str = """
    //     Hello World
    // """.trimIndent()
    // print(str)

    base.demo2()
}