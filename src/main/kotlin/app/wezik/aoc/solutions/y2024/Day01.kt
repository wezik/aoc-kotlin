package app.wezik.aoc.y2024

import kotlin.math.abs
import java.io.File

fun part1(input: File) = input.readLines().getDistance().toString()
fun part2(input: File) = input.readLines().getSimilarity().toString()

private fun List<String>.getSimilarity(): Int {
    val split = toSeparate()
    val cache = mutableMapOf<Int, Int>()
    return split.first.sumOf{ a ->
        cache.getOrPut(a) { a * split.second.count { b -> a == b } }
    }
}

private fun List<String>.getDistance() = this.toSeparate().sortedZip().sumOf { abs(it.first - it.second) }

private fun Pair<List<Int>, List<Int>>.sortedZip() = this.first.sorted().zip(this.second.sorted())

private fun List<String>.toSeparate() = map { line ->
    line.split(' ').filter { it.isNotBlank() }.map { it.toInt() }
}.let {
    Pair(it.map { it[0] }, it.map { it[1] })
}
