package org.example.solution.solver.days

import org.example.solution.solver.Solver
import org.example.solution.solver.Result
import org.example.solution.time
import org.example.solution.toNanoDuration
import kotlin.math.abs
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class Day1Solver : Solver {

    override fun solve(input: List<String>): Pair<Result, Result> {
        // Early return on empty input
        if (input.isEmpty()) return Pair(
            Result("0", 0.toDuration(DurationUnit.MICROSECONDS)),
            Result("0", 0.toDuration(DurationUnit.MICROSECONDS))
        )

        var distance = 0
        val distanceTime = time {
            distance = input.getDistance()
        }
        val distanceResult = Result(distance.toString(), distanceTime.toNanoDuration())

        var similarity = 0
        val similarityTime = time {
            similarity = input.getSimilarity()
        }
        var similarityResult = Result(similarity.toString(), similarityTime.toNanoDuration())

        return distanceResult to similarityResult
    }

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
