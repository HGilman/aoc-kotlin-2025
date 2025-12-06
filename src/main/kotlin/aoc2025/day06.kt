package aoc2025

private const val EMPTY_SYMBOL = "n"
private const val EMPTY_SYMBOL_CHAR = 'n'

fun partOne() {

    val input: List<List<String>> = readInput("day06").map { line ->
        line.split("\\s+".toRegex()).filterNot { it.isEmpty() }
    }

    val operations = input.last()
    val width = input[0].size

    val amountOfVerticalNumbers = input.size - 1
    val result: MutableList<Long> = mutableListOf()

    (0 until width).forEach { x ->
        val operation = operations[x]
        val resultOfOperation = when (operation) {
            "*" -> {
                (0 until amountOfVerticalNumbers).fold(1L) { acc, y ->
                    acc * input[y][x].toInt()
                }
            }

            else -> {
                (0 until amountOfVerticalNumbers).fold(0L) { acc, y ->
                    acc + input[y][x].toInt()
                }
            }
        }
        result.add(resultOfOperation)
    }
    println(result.sum())

}

fun partTwo() {
    val input: List<List<Char>> = readInput("day06").map { line ->
        line.split(" ").map {
            if (it == "" || it == " ") EMPTY_SYMBOL else it
        }.map { it.toCharArray().toList() }.flatten()
    }

    val bottomLine: List<Char> = input[input.size - 1]
    val width = bottomLine.size

    var startBottomIndex = 0
    var endBottomIndex = 0

    var result: Long = 0L

    while (startBottomIndex != input[0].size) {

        val operation = bottomLine[startBottomIndex]

        endBottomIndex = startBottomIndex + 1

        while (endBottomIndex <= width - 1 && bottomLine[endBottomIndex] == EMPTY_SYMBOL_CHAR) {
            endBottomIndex++
        }
        endBottomIndex -= 1

        val numbers: MutableList<Int> = mutableListOf<Int>()
        var testNumber = ""
        (endBottomIndex downTo startBottomIndex).forEach { x ->

            var number = ""

            testNumber += input[0][x]

            (0 until input.size - 1).forEach { y ->
                number += input[y][x]
            }
            number = number.replace(EMPTY_SYMBOL, "")
            numbers.add(number.toInt())
        }
        result += when(operation) {
            '*' -> {
                numbers.fold(1L) { acc, n ->
                    acc * n
                }
            }

            else -> {
                numbers.fold(0L) { acc, n ->
                    acc + n
                }
            }
        }
        startBottomIndex = endBottomIndex + 1
    }
    println(result)
}


fun main() {
    partOne()
    partTwo()
}