package base

enum class Direction {
    NORTH, SOUTH, EAST, WEST
}

enum class Color(val value: String) {
    RED("warm"), GREEN("cool"), BLUE("cool")
}

fun main() {
    val direction = Direction.NORTH
    println("Direction: $direction")

    val color = Color.RED
    println("Color: $color")
    println("Color type: ${Color.RED.value}")
}
