package aoc2025

import kotlin.math.abs

fun main() {

//    part1()
    printInput(readInput("day09_test"))
//    printInput(readInput("day09"))
}

fun printInput(input: List<String>) {
    val points = input.map { s ->
        val (x, y) = s.split(',').map { it.toInt() - 1 }
        Point2d(x, y)
    }.toSet()

    val maxX = points.maxBy { it.x }.x
    val maxY = points.maxBy { it.y }.y

    val map = List(maxY + 1) { y ->
        List(maxX + 1) { x ->
            val value = if (points.contains(Point2d(x, y))) 1 else 0
            if (value == 1) print('#') else print('.')
            value
        }
        println()
    }
}

private fun part1 () {
    val input = readInput("day09")
    val map: List<Point2d> = input.map { s ->
        val (x, y) = s.split(',').map { it.toInt() }
        Point2d(x, y)
    }
    val size = map.size
    val areas = mutableListOf<Long>()
    for (i in 0 until size - 1) {
        for (j in i + 1 until size) {
            areas += map[i].squareTo(map[j])
        }
    }
    val maxArea = areas.max()
    println(maxArea)
}


data class Point2d(
    val x: Int,
    val y: Int
) {
    fun squareTo(other: Point2d): Long {
        return (abs(other.y - y.toLong()) + 1) * (abs(other.x - x) + 1)
    }
}