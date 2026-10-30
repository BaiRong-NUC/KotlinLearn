package `class`

open class Animal(val name: String, val age: Int) {

    override fun toString(): String {
        return "Animal(name=$name, age=$age)"
    }

    open fun sound() {
        println("$name makes a sound")
    }
}

class Dog(name: String, age: Int) : Animal(name, age) {
    override fun toString(): String {
        return "Dog(name=$name, age=$age)"
    }

    override fun sound() {
        super.sound()
        println("$name barks")
    }
}

fun main() {
    // val animal = Animal("Dog", 5)
    // println(animal)
    // animal.sound()

    Dog("Buddy", 3).sound()
    println(Dog("Buddy", 3))
}