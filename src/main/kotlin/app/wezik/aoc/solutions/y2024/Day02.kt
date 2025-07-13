package app.wezik.aoc.solutions.y2024

import app.wezik.aoc.domain.Solution
import app.wezik.aoc.domain.SolutionInput
import app.wezik.aoc.domain.SolutionResult
import app.wezik.aoc.domain.SolutionResult.Success

object Day02 : Solution() {

    override fun part1(input: SolutionInput): SolutionResult {
        // parse data into list of reports (list of level lists)
        val reports = input.lines
            .map { it.split(' ').map { it.toInt() } }

        fun isSafe(levels: List<Int>): Boolean {
            val diffs = levels.zipWithNext().map { (a, b) -> a - b }
            return diffs.all { it in 1..3 } or diffs.all { it in -3..-1 }
        }

        val result = reports.count { isSafe(it) }
        return Success(result)
    }

    override fun part2(input: SolutionInput): SolutionResult {
        val reports = input.lines
            .map { it.split(' ').map { it.toInt() } }

        fun isSafe(levels: List<Int>): Boolean {
            val diffs = levels.zipWithNext().map { (a, b) -> a - b }
            return diffs.all { it in 1..3 } or diffs.all { it in -3..-1 }
        }

        var count = 0
        for (levels in reports) {
             // early return if it is already safe
            if (isSafe(levels)) {
                count++
                continue
            }

            for (n in 0 until levels.size) {
                // remove one by one through the list, brute forcing
                val trimmed = levels.subList(0, n) + levels.subList(n + 1, levels.size)
                if (isSafe(trimmed)) {
                    count++
                    break
                }
            }
        }

        return Success(count)
    }
}
