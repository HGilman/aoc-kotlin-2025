package aoc2025


fun main() {

    fun part1(input: List<String>): Long {
        return calculateInvalidIds(
            createRanges(input.first()),
            ::isNotValidPart1
        )
    }

    fun part2(input: List<String>): Long {
        return calculateInvalidIds(
            createRanges(input.first()),
            ::isNotValidPart2
        )
    }

    val input = readInput("day02")
    println("part1: ${part1(input)}")
    println("part2: ${part2(input)}")
}


fun calculateInvalidIds(
    ranges: List<LongRange>,
    isNotValid : (id: Long) -> Boolean
): Long {
    var result = 0L
    ranges.forEach { range ->
        range.forEach { it ->
            if (isNotValid(it)) {
                result += it
            }
        }
    }
    return result
}

fun isNotValidPart1(id: Long): Boolean {
    val idString = id.toString()
    val length = idString.length

    if (length.mod(2) != 0) return false

    val first = idString.substring(0, length / 2)
    val second = idString.subSequence(length / 2, length)

    if (first[0] == '0' || second[0] == '0') return false
    return first == second
}

fun isNotValidPart2(id: Long): Boolean {
    val idString = id.toString()
    val length = idString.length

    for (i in (1..(length / 2))) {
        val part = idString.substring(0, i)
        if (length.mod(i) != 0) continue

        val times = length / i

        var tryConstruct = ""
        repeat(times) {
            tryConstruct += part
        }
        if (tryConstruct == idString) return true
    }
    return false
}

fun createRanges(input: String): List<LongRange> {
    return input.split(',').map {
        val (from, to) = it.split('-').map { it.toLong() }
        from..to
    }
}
