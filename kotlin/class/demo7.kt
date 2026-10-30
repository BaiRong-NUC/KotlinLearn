package `class`

interface IAnimal {
    val name: String
    val age: Int
    fun sound()
}

interface IAnimation {
    val speed: Int
    fun animate()
}

class Cat(override val name: String, override val age: Int, override val speed: Int) : IAnimal, IAnimation {

    override fun sound() {
        println("$name meows")
    }

    override fun animate() {
        println("$name is jumping")
    }
}


fun main() {
    val cat = Cat("Whiskers", 2, 10)
    cat.sound()
    cat.animate()
}