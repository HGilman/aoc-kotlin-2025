package aoc2025


fun main() {

    fun part1(map: List<List<Char>>): Int {
        val h = map.size
        val w = map[0].size
        val splitterMap: Array<Array<Int>> = Array(h) { y ->
            Array(w) { x ->
                0
            }
        }
        val x0 = w / 2
        passThrowMapRecursive(x0, 0, map, splitterMap)
        return splitterMap.sumOf { line ->
            line.sum()
        }
    }

    fun part2(map: List<List<Char>>): Long {
        val w = map[0].size
        val x0 = w / 2
        val counter = Counter()
        passThrowMapRecursive2(x0, 0, map, counter)
        return counter.count
    }

    val testInput = readInput("day07_test")
    val input = readInput("day07")
//    val input = testInput

    val map: List<List<Char>> = input.map { it.toCharArray().toList() }
//    println(part1(map))
    println(part2(map))
}

fun passThrowMapRecursive(
    x: Int,
    y: Int,
    map: List<List<Char>>,
    splitterMap: Array<Array<Int>>
) {
    val h = map.size
    val w = map[0].size

    if (y >= h || x < 0 || x >= w) return

    val current = map[y][x]

    if (current == '.') {
        passThrowMapRecursive(x, y + 1, map, splitterMap)
    } else {
        val isPassedAlready = splitterMap[y][x] == 1
        if (!isPassedAlready) {
            splitterMap[y][x] = 1
            passThrowMapRecursive(x - 1, y, map, splitterMap)
            passThrowMapRecursive(x + 1, y, map, splitterMap)
        }
    }
}

fun passThrowMapRecursive2(
    x: Int,
    y: Int,
    map: List<List<Char>>,
    counter: Counter
) {
    val h = map.size
    val w = map[0].size

    if (y >= h || x < 0 || x >= w) {
        counter.count++
        return
    }
    val current = map[y][x]

    if (current == '.') {
        passThrowMapRecursive2(x, y + 1, map, counter)
    } else {
        passThrowMapRecursive2(x - 1, y, map, counter)
        passThrowMapRecursive2(x + 1, y, map, counter)
    }
}

class Node(
    var left: Node? = null,
    var right: Node? = null
) {
    fun calculatePaths(counter: Counter) {
        if (left == null && right == null) {
            counter.count++
            println(counter.count)
        }
        left?.calculatePaths(counter)
        right?.calculatePaths(counter)
    }
}

class Counter {
    var count: Long = 0L
}






