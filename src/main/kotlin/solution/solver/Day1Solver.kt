package org.example.solution.solver

import org.example.solution.InputSolver
import kotlin.math.abs

class Day1Solver : InputSolver {

    override fun solve(input: List<String>): Pair<String, String> {
        // Early return on empty input
        if (input.isEmpty()) return Pair("0", "0")

        val pairs = input.toPairs()

        val split = pairs.split()

        val distance = split.sortedZip().map {
            abs(it.first - it.second)
        }.sum()

        val cache = mutableMapOf<Int, Int>()

        val similarity = split.first.map { a ->
            // Early return if cached
            if (cache.containsKey(a)) return@map cache[a]!!

            val weight = split.second.count { b -> a == b }

            val result = a * weight
            // Cache it
            cache[a] = result
            result
        }.sum()

        return Pair("$distance", "$similarity")
    }

    private fun Pair<List<Int>, List<Int>>.sortedZip(): List<Pair<Int, Int>> {
        return this.first.sorted().zip(this.second.sorted())
    }

    private fun List<String>.toPairs(): List<Pair<Int, Int>> {
        return this.map { line ->
            val parts = line.split(' ').filter { it.isNotBlank() }
            Pair(parts[0].toInt(), parts[1].toInt())
        }
    }

    private fun List<Pair<Int, Int>>.split(): Pair<List<Int>, List<Int>> {
        return Pair(this.map { it.first }, this.map { it.second })
    }
}
