package aoc2025


fun main() {

    /** It my solution */
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

    /** It seemed that solution of part 1 didn't work for part two, so i should find different approach*/
    fun part2(map: List<List<Char>>): Long {
        return calculatePaths(map)
    }

//    val input = readInput("day07_test")
    val input = readInput("day07")

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


// initially there is one beam in center of grid
fun calculatePaths(
    grid: List<List<Char>>
): Long {
    val h = grid.size
    val w = grid[0].size
    var previousLinePaths = mutableMapOf(grid[0].size / 2 to 1L)
    for (y in 2 until h - 1) {
        val line = grid[y]
        val currentLinePaths = mutableMapOf<Int, Long>()
        for (x in 0 until w) {
            val amount = previousLinePaths.getOrDefault(x, 0)
            if (line[x] != '.') {
                currentLinePaths[x - 1] = currentLinePaths.getOrDefault(x - 1, 0) + amount
                currentLinePaths[x + 1] = currentLinePaths.getOrDefault(x + 1, 0) + amount
            } else {
                currentLinePaths[x] = currentLinePaths.getOrDefault(x, 0) + amount
            }
        }
        previousLinePaths = currentLinePaths
    }
    return previousLinePaths.values.sum()
}





