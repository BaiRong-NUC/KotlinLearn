package `class`

data class Iobj(val name: String, val age: Int)

//单例类
object Singleton {
    const val TCOKEN = "123456"
    fun showMessage() {
        println("This is a singleton object with token $TCOKEN")
    }
}

fun main() {
    val obj = Iobj("Alice", 25)
    println(obj)

    val obj2 = Iobj("Bob", 30)
    println(obj == obj2)

    val obj3 = obj.copy(name = "Charlie")
    println(obj3)

    // 解构
    val (name, age) = obj3
    println("Name: $name, Age: $age")

    Singleton.showMessage()
}