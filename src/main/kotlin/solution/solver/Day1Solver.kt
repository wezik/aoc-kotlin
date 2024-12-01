package org.example.solution.solver

import org.example.solution.InputSolver
import org.example.solution.Result
import kotlin.math.abs
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class Day1Solver : InputSolver {

    override fun solve(input: List<String>): Pair<Result, Result> {
        // Early return on empty input
        if (input.isEmpty()) return Pair(
            Result("0", 0.toDuration(DurationUnit.MICROSECONDS)),
            Result("0", 0.toDuration(DurationUnit.MICROSECONDS))
        )

        var distance = 0
        val distanceTime = time {
            distance = input.toDistance()
        }

        var similarity = 0
        val similarityTime = time {
            similarity = input.toSimilarity()
        }

        return Result(distance.toString(), distanceTime.toDuration(DurationUnit.NANOSECONDS)) to
                Result(similarity.toString(), similarityTime.toDuration(DurationUnit.NANOSECONDS))

    }

    private fun time(block: () -> Unit): Long {
        val start = System.nanoTime()
        block()
        return System.nanoTime() - start
    }

    private fun List<String>.toSimilarity(): Int {
        val pairs = this.toPairs()

        val split = pairs.split()

        val cache = mutableMapOf<Int, Int>()

        return split.first.map { a ->
            // Early return if cached
            if (cache.containsKey(a)) return@map cache[a]!!

            val weight = split.second.count { b -> a == b }

            val result = a * weight
            // Cache it
            cache[a] = result
            result
        }.sum()
    }

    private fun List<String>.toDistance(): Int {
        val pairs = this.toPairs()

        val split = pairs.split()

        return split.sortedZip().map {
            abs(it.first - it.second)
        }.sum()
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
