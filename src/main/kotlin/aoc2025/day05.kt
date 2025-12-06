package aoc2025

import kotlin.math.max

fun main() {
    val testInput = readInput("day05_test")
    val input = readInput("day05")
//    val input = testInput

    val spaceIndex = input.indexOf("")

    val ranges: List<String> = input.subList(0, spaceIndex)
    val ids: List<String> = input.subList(spaceIndex + 1, input.size)

    val longRanges = ranges.map { it.toLongRange() }
    val longIds = ids.map { it.toLong() }

//    println(part1(longRanges, longIds))

    println(part2(longRanges))
}

private fun part1(
    ranges: List<LongRange>,
    ids: List<Long>
): Int {
    return ids.count { id ->
        ranges.any { range ->
            range.contains(id)
        }
    }
}

private fun part2(ranges: List<LongRange>): Long {

    val sortedRanges = ranges.sortedBy { it.first }
    val overlapped = sortedRanges.fold(mutableListOf()) {
        acc: MutableList<LongRange>, nextRange: LongRange ->

        if (acc.isEmpty()) {
            acc.add(nextRange)
        } else {
            val previousRange = acc.last()
            if (previousRange.last < nextRange.first) {
                acc.add(nextRange)
            } else if (previousRange.contains(nextRange.first)) {
                val last = max(previousRange.last, nextRange.last)
                val newRange = LongRange(previousRange.first, last)
                acc.removeLast()
                acc.add(newRange)
            }
        }
        acc
    }
    return overlapped.sumOf { it.last - it.first + 1 }
}

fun String.toLongRange(): LongRange {
    val (from, to) = split("-").map { it.toLong() }
    return LongRange(from, to)
}
