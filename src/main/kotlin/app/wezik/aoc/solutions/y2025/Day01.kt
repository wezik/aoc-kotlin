package app.wezik.aoc.solutions.y2025

import app.wezik.aoc.domain.Solution
import app.wezik.aoc.domain.SolutionInput
import app.wezik.aoc.domain.SolutionResult
import app.wezik.aoc.domain.SolutionResult.NotImplemented
import kotlin.math.abs

object Day01 : Solution() {

    override fun part1(input: SolutionInput): SolutionResult {
        val inputs = input.lines
            .filter { it.length > 1 }
            .map {
                val distance = it.subSequence(1, it.length).toString().toInt()
                when (it[0]) {
                    'L' -> -distance
                    'R' -> distance
                    else -> throw IllegalArgumentException("invalid direction")
                }
            }

        // NOTE: Dial starts by pointing at 50
        var dial = 50
        var sum = 0

        for (distance in inputs) {
            dial += distance
            when {
                dial > 0 -> while (dial >= 100) dial -= 100
                dial < 0 -> while (dial < 0) dial += 100
            }

            if (dial == 0) sum++
        }

        return SolutionResult.Success(sum)
    }

    override fun part2(input: SolutionInput) = NotImplemented
}
