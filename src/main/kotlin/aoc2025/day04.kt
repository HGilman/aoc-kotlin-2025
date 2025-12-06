package aoc2025

fun main() {

    val testInput = readInput("day04_test")
    val input = readInput("day04")
//    println(part1(testInput))
    println(part1(input))
    println(part2(testInput))
    println(part2(input))
}

private fun part1(input: List<String>): Int {
    val map = createMap(input)
    return findAccessiblePapers(map).size
}

private fun part2(input: List<String>): Int {
    val map = createMap(input)
    var toBeRemovedPapersPositions = findAccessiblePapers(map)
    var result = toBeRemovedPapersPositions.size

    while (toBeRemovedPapersPositions.isNotEmpty()) {
        toBeRemovedPapersPositions.forEach { (x, y) ->
            map[y][x] = 0
        }
        toBeRemovedPapersPositions = findAccessiblePapers(map)
        result += toBeRemovedPapersPositions.size
    }

    return result
}


fun findAccessiblePapers(map: List<List<Int>>): List<Pair<Int, Int>> {
    val h = map.size
    val w = map[0].size

    val result = mutableListOf<Pair<Int, Int>>()

    (0 until h).forEach { y ->
        (0 until w).forEach { x ->
            val papersAround = calculatePapersAround(map, x, y)
            val isPaper = map[y][x] == 1
            if (isPaper && papersAround < 4) {
                result.add(x to y)
            }
        }
    }
    return result
}

fun calculatePapersAround(map: List<List<Int>>, x: Int, y: Int): Int {
    val h = map.size
    val w = map[0].size

    val left = if (x - 1 < 0) 0 else map[y][x-1]
    val leftUp = if (y - 1 < 0 || x - 1 < 0) 0 else map[y - 1][x - 1]
    val up = if (y - 1 < 0)  0  else map[y - 1][x]
    val upRight = if (y - 1 < 0 || x + 1 >= w) 0 else map[y - 1][x + 1]
    val right = if (x + 1 >= w) 0 else map[y][x + 1]
    val rightDown = if (x + 1 >= w || y + 1 >= h) 0 else map[y + 1][x + 1]
    val down = if (y + 1 >= h) 0 else map[y + 1][x]
    val downLeft = if (y + 1 >= h || x - 1 < 0) 0  else map[y + 1][x - 1]

    return left + leftUp + up + upRight + right + rightDown + down + downLeft
}

fun createMap(input: List<String>): MutableList<MutableList<Int>> {
    return input.map {
        it.toCharArray().map { c ->
            if (c == '@') 1 else 0
        }.toMutableList()
    }.toMutableList()
}
