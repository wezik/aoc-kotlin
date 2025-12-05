package app.wezik.aoc.solutions.y2025

import app.wezik.aoc.domain.Solution
import app.wezik.aoc.domain.SolutionInput
import app.wezik.aoc.domain.SolutionResult
import app.wezik.aoc.domain.SolutionResult.NotImplemented
import kotlin.math.abs
import kotlin.math.absoluteValue

object Day02 : Solution() {

    private fun SolutionInput.parse(): List<Pair<Long, Long>> {
        return lines
            .flatMap { line ->
                line.split(",").map { range ->
                    val (start, end) = range.split("-").map { it.toLong() }
                    start to end
                }
            }
    }

    override fun part1(input: SolutionInput): SolutionResult {
        val inputs = input.parse()

        var sum = 0L
        for ((start, end) in inputs) {
            for (i in start..end) {
                val strI = i.toString()
                if (strI.length % 2 != 0) continue

                val middle = strI.length / 2

                if (strI.substring(0, middle) == strI.substring(middle)) {
                    sum += i
                }
            }
        }

        return SolutionResult.Success(sum)
    }

    override fun part2(input: SolutionInput): SolutionResult {
        val inputs = input.parse()

        var sum = 0L
        for ((start, end) in inputs) {
            outer@ for (n in start..end) {
                val strI = n.toString()
                val repCounts = (1..strI.length - 1).filter { strI.length % it == 0 }
                for (repCount in repCounts) {
                    val rep = strI.substring(0, repCount)
                    val chunked = strI.chunked(repCount)
                    if (chunked.all { it == rep }) {
                        sum += n
                        continue@outer
                    }
                }
            }
        }

        return SolutionResult.Success(sum)

    }
}
