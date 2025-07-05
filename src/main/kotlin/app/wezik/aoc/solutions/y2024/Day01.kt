package app.wezik.aoc.solutions.y2024

import app.wezik.aoc.domain.Solution
import app.wezik.aoc.domain.SolutionInput
import kotlin.math.abs

object Day01 : Solution(
    day = 1,
    year = 2024,
) {
    override fun part1(input: SolutionInput) = input.file.readLines().getDistance().toString()
    override fun part2(input: SolutionInput) = input.file.readLines().getSimilarity().toString()

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
}
