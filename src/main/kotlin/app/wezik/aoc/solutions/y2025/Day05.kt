package app.wezik.aoc.solutions.y2025

import app.wezik.aoc.domain.Solution
import app.wezik.aoc.domain.SolutionInput
import app.wezik.aoc.domain.SolutionResult
import app.wezik.aoc.domain.SolutionResult.NotImplemented
import kotlin.math.abs
import kotlin.math.absoluteValue

object Day05 : Solution() {

    private fun SolutionInput.parse(): Pair<List<LongRange>, List<Long>> {
        val (rangesContent, idsContent) = this.content.split("\n\n")

        val ranges = rangesContent
            .split("\n")
            .filter { it.isNotBlank() }
            .map { 
                val (start, end) = it.split("-").map { it.toLong() }
                start..end
            }

        val ids = idsContent
            .split("\n")
            .filter { it.isNotBlank() }
            .map { it.toLong() }

        return ranges to ids
    }

    override fun part1(input: SolutionInput): SolutionResult {
        val (ranges, ids) = input.parse()
        val sum = ids.count { id -> ranges.any { range -> id in range } }
        return SolutionResult.Success(sum)
    }

    override fun part2(input: SolutionInput): SolutionResult {
        val (ranges, _) = input.parse()
        var reducedRanges = mutableListOf<LongRange>()
        for (range in ranges) {
            val lowerBoundFinds = reducedRanges.withIndex().filter { (_, v) -> range.start in v }
            val upperBoundFinds = reducedRanges.withIndex().filter { (_, v) -> range.endInclusive in v }
            val containedFinds = reducedRanges.withIndex().filter { (_, v) -> v.start in range && v.endInclusive in range }

            if (lowerBoundFinds.isNotEmpty() && upperBoundFinds.isNotEmpty()) {
                // merge
                val max = upperBoundFinds.maxOf { it.value.endInclusive }
                val min = lowerBoundFinds.minOf { it.value.start }

                var foo = reducedRanges.withIndex().find { max in it.value || min in it.value }
                while (foo != null) {
                    reducedRanges.removeAt(foo.index)
                    foo = reducedRanges.withIndex().find { max in it.value || min in it.value }
                }
                reducedRanges.add(min..max)
            } else if (lowerBoundFinds.isNotEmpty()) {
                // extend upper bounds
                for ((i, v) in lowerBoundFinds) {
                    reducedRanges[i] = v.start..range.endInclusive
                }
            } else if (upperBoundFinds.isNotEmpty()) {
                // extend lower bounds
                for ((i, v) in upperBoundFinds) {
                    reducedRanges[i] = range.start..v.endInclusive
                }
            } else if (containedFinds.isNotEmpty()) {
                // contained finds like 10..40 for existing 20..30 have to be handled explicitly
                var foo = reducedRanges.withIndex().find { it.value.start in range && it.value.endInclusive in range }
                while (foo != null) {
                    reducedRanges.removeAt(foo.index)
                    foo = reducedRanges.withIndex().find { it.value.start in range && it.value.endInclusive in range }
                }

                reducedRanges.add(range)
            } else {
                // just add as its missing
                reducedRanges.add(range)
            }
        }

        var sum = 0L
        for (range in reducedRanges) sum += range.endInclusive - range.start + 1

        return SolutionResult.Success(sum)
    }
}
