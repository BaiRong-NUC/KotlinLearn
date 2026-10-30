package `class`

class Person {
    var name: String = ""
    var age: Int = 0

    fun print() {
        println("Name: $name, Age: $age")
    }
}

// 显式主构造函数
class Student(var name: String, var score: Int) {
    fun print() {
        println("Name: $name, Score: $score")
    }
}

// 辅助构造函数
class Car {
    var name: String = ""
    var price: Double = 0.0

    constructor(name: String, price: Double) {
        this.name = name
        this.price = price
    }
}

// 若类中有显式主构造函数,所有的辅助构造函数都必须委托给主构造函数
class Bike(var name: String) {
    var price: Double = 0.0

    constructor(name: String, price: Double) : this(name) {
        this.price = price
    }
}


// init代码块
class Teacher(name: String, age: Int) {
    init {
        // 初始化代码块在主构造函数执行后立即执行，主要用于检测构造函数传入的参数是否合法等操作
        println("Teacher initialized with name: $name, age: $age")
    }
}

fun main() {
    val p = Person()
    p.name = "John"
    p.age = 30
    p.print()

    println(p.toString())

    val s = Student("Alice", 95)
    s.print()
}
