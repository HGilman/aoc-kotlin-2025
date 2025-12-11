package aoc2025

import kotlin.math.pow
import kotlin.math.sqrt

fun main() {
//    val input = readInput("day08_test")
    val input = readInput("day08")

    val coordinates: List<Coordinates3d> = input.map { line ->
        val (x, y, z) = line.split(',').map { it.trim().toDouble() }
        Coordinates3d(x, y, z)
    }
    println(part1(coordinates))

}

private fun part1(
    coordinates: List<Coordinates3d>
): Int {
    val shortestDistances = createDistances(coordinates).sortedBy { it.distance }
    val largest = findLargestClusters(
        shortestLines = shortestDistances,
        amountOfConnections = 1000,
        amountOfClusters = 3
    )
    return largest
        .map { it.size }
        .reduce(Int::times)
}

data class Coordinates3d(
    val x: Double,
    val y: Double,
    val z: Double
) {
    fun distanceTo(other: Coordinates3d): Double {
        return sqrt((other.x - x).pow(2) + (other.y - y).pow(2) + (other.z - z).pow(2))
    }
}

data class Line(
    val i: Int,
    val firstCoordinates: Coordinates3d,
    val j: Int,
    val secondCoordinates: Coordinates3d
) {
    val distance: Double by lazy {
        secondCoordinates.distanceTo(firstCoordinates)
    }

    override fun toString(): String {
        return "i: $i, j: $j, x1: $firstCoordinates, x2: $secondCoordinates, d: $distance"
    }
}

private fun createDistances(coordinates: List<Coordinates3d>): List<Line> {
    val size = coordinates.size
    val pairs = mutableListOf<Line>()
    for (i in 0 until size - 1) {
        for (j in i + 1 until size) {
            pairs += Line(
                i = i,
                firstCoordinates = coordinates[i],
                j = j,
                secondCoordinates = coordinates[j]
            )
        }
    }
    return pairs
}

private fun findLargestClusters(
    shortestLines: List<Line>,
    amountOfConnections: Int,
    amountOfClusters: Int
): List<Set<Int>> {

    val clusters = mutableListOf<MutableSet<Int>>()
    var connectionsCounter = 0

    for (line in shortestLines) {
        val i = line.i
        val j = line.j
        val iCluster: MutableSet<Int>? = clusters.find { it.contains(i) }
        val jCluster: MutableSet<Int>? = clusters.find { it.contains(j) }

        if (connectionsCounter < amountOfConnections) {
            when {
                iCluster == null && jCluster == null -> {
                    val newCluster = mutableSetOf(i, j)
                    clusters.add(newCluster)
                }
                iCluster != null && jCluster == null -> {
                    iCluster.add(i)
                    iCluster.add(j)
                }
                iCluster == null && jCluster != null -> {
                    jCluster.add(j)
                    jCluster.add(i)
                }
                iCluster != null && jCluster != null -> {
                    if (iCluster != jCluster) {
                        iCluster.addAll(jCluster)
                        jCluster.clear()
                    }
                }
            }
            connectionsCounter++
        }
    }
    return clusters.sortedBy { it.size }.reversed().take(amountOfClusters)
}



































