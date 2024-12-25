package app.wezik.aoc.y2024

import app.wezik.aoc.Day
import kotlin.math.abs
import java.io.File

class Day01 : Day {

    override fun part1(input: File) = input.readLines().getDistance().toString()
    override fun part2(input: File) = input.readLines().getSimilarity().toString()

    private fun List<String>.getSimilarity(): Int {
        val split = this.toSeparate()
        val cache = mutableMapOf<Int, Int>()
        return split.first.map { a ->
            cache.getOrPut(a) { a * split.second.count { b -> a == b } }
        }.sum()
    }

    private fun List<String>.getDistance(): Int {
        return this.toSeparate()
            .sortedZip()
            .map { abs(it.first - it.second) }
            .sum()
    }

    private fun Pair<List<Int>, List<Int>>.sortedZip(): List<Pair<Int, Int>> {
        return this.first.sorted().zip(this.second.sorted())
    }

    private fun List<String>.toSeparate(): Pair<List<Int>, List<Int>> {
        this.map { line ->
            line.split(' ').filter { it.isNotBlank() }.map { it.toInt() }
        }.let {
            return Pair(it.map { it[0] }, it.map { it[1] })
        }
    }
}
