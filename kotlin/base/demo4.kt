package base

data class Car(
    val make: String,
    val model: String,
    val year: Int
)

fun main() {
    val cars = listOf(
        base.Car("Toyota", "Corolla", 2020),
        base.Car("Honda", "Civic", 2019),
        base.Car("Ford", "Mustang", 2021)
    )
    println("All cars:")
    for (car in cars) {
        println(car)
    }

    println("Recent cars:")
    // filter 过滤
    val recentCars = cars.filter { it.year >= 2020 }
    for (car in recentCars) {
        println(car)
    }

    println("Sorted cars:")
    //sort 排序
    val sortedCars = cars.sortedBy { it.year }
    for (car in sortedCars) {
        println(car)
    }

    println("Reverse sorted cars:")
    // reverse 排序
    val reverseSortedCars = cars.sortedByDescending { it.year }
    for (car in reverseSortedCars) {
        println(car)
    }

    print("All cars using forEach:")
    // 遍历
    cars.forEach { println(it) }

    println(cars.any { it.year >= 2020 }) // 检查是否有年份大于等于2020的汽车

    println(cars.all { it.year >= 2019 }) // 检查是否所有汽车的年份都大于等于2019

    println(cars.none { it.year < 2019 }) // 检查是否没有年份小于2019的汽车

    // 链式调用
    val recentSortedCars = cars.filter { it.year >= 2020 }.sortedBy { it.year }
    for (car in recentSortedCars) {
        println(car)
    }
}
