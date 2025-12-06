package aoc2025

import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String): List<String> = Path("src/main/kotlin/aoc2025/input/$name.txt").readText().trim().lines()

fun readInputForNotebook(name: String): List<String> = Path("/Users/hasan/projects/aoc-kotlin-2025/src/main/kotlin/aoc2025/input/$name.txt")
    .readText()
    .trim()
    .lines()

/**
 * Converts string to aoc.md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)
