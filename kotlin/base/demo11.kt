package base

class Truck(val length: Int) {
    fun printLength() {
        println("Truck length: $length")
    }

    fun doubleLength() {
        println("Truck double length: ${length * 2}")
    }

    fun tripleLength() {
        println("Truck triple length: ${length * 3}")
    }
}

class Phone {
    var brand: String = "Unknown"
    var model: String = "Unknown"

    override fun toString(): String {
        return "Phone(brand='$brand', model='$model')"
    }
}


fun computer(a: Int, b: Int, callback: (Int, Int) -> Unit) {
    callback(a, b)
}

fun test(str: String? = null) {
    str?.let {
        // str 不为空
        println("str is not null: $it")
    }
}

fun main() {
    test("Hello")

    with(Truck(5)) {
        printLength()
        doubleLength()
        tripleLength()
    }

    val phone = Phone().apply {
        brand = "Samsung"
        model = "Galaxy S21"
    }

    println(phone)
}
