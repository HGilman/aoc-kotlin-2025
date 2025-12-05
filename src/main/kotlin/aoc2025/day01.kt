package aoc2025

fun main() {

    fun part1(input: List<String>): Int {
        val commands = input.map {
            it[0] to it.substring(1).toInt()
        }
        return calculateZeros(commands)
    }

    fun part2(input: List<String>): Int {
        val commands = input.map {
            val direction = it[0]
            val amount = it.substring(1).toInt()
            (0 until amount).map { direction to 1}
        }.flatten()
        return calculateZeros(commands)
    }


    val testInput = readInput("day01_test")
    part1(testInput)

    val input = readInput("day01")
    part1(input).println()
    part2(input).println()
}

fun calculateZeros(input: List<Pair<Char, Int>>): Int {
    var zeroCounter = 0
    var current = 50

    for ((direction, amount) in input) {
        current += if (direction == 'L') -amount else amount
        current = current.mod(100)
        if (current == 0) {
            zeroCounter++
        }
    }
    return zeroCounter
}
