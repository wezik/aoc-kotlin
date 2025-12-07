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
        return SolutionResult.Success(0)
    }
}
