package aoc2025

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun main() {

//    part1()
//    printInput(readInput("day09_test"))
//    printInput(readInput("day09"))

    println(part2(readInput("day09")))
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

private fun part1() {
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

private fun part2(input: List<String>): Long {

    val points: List<Point2d> = input.map { s ->
        val (x, y) = s.split(',').map { it.toInt() }
        Point2d(x, y)
    }

    val size = points.size
    var rectangles = mutableListOf<Pair<Point2d, Point2d>>()
    for (i in 0 until size - 1) {
        for (j in i + 1 until size) {
            val first = points[i]
            val second = points[j]
            rectangles.add(first to second)
        }
    }
    rectangles = rectangles.map { it.sort() }.toMutableList()

    val polygonLines = points
        .plus(points.first())
        .zipWithNext()
        .map { it.sort() }

    // for every rectangle, check if intersects any green line, if none, its a valid rectangle inside the big one
    return rectangles.filter { (r1, r2) ->
        polygonLines.none { (p1, p2) ->
            r1.x < p2.x && r2.x > p1.x && r1.y < p2.y && r2.y > p1.y
        }
    }.maxOf { it.first.squareTo(it.second) }
}


fun Pair<Point2d, Point2d>.sort(): Pair<Point2d, Point2d> = Pair(
    Point2d(min(first.x, second.x), min(first.y, second.y)),
    Point2d(max(first.x, second.x), max(first.y, second.y))
)

data class Point2d(
    val x: Int,
    val y: Int
) {
    fun squareTo(other: Point2d): Long {
        return (abs(other.y - y.toLong()) + 1) * (abs(other.x - x) + 1)
    }
}

data class Vector2d(
    val from: Point2d,
    val to: Point2d
) {

    fun vectorMultiply(other: Vector2d): Int {
        val ax = to.x - from.x
        val ay = to.y - from.y

        val bx = other.to.x - other.from.x
        val by = other.to.y - other.from.y

        return ax * by - ay * bx
    }
}