package app.wezik.aoc.solutions.y2025

import app.wezik.aoc.domain.Solution
import app.wezik.aoc.domain.SolutionInput
import app.wezik.aoc.domain.SolutionResult
import app.wezik.aoc.domain.SolutionResult.NotImplemented
import kotlin.math.abs
import kotlin.math.absoluteValue

object Day01 : Solution() {

    private fun SolutionInput.parse(): List<Int> {
        return lines
        .filter { it.length > 1 }
        .map {
            val distance = it.subSequence(1, it.length).toString().toInt()
            when (it[0]) {
                'L' -> -distance
                'R' -> distance
                else -> throw IllegalArgumentException("invalid direction")
            }
        }
    }

    override fun part1(input: SolutionInput): SolutionResult {
        val inputs = input.parse()

        // NOTE: Dial starts by pointing at 50
        var dial = 50
        var sum = 0

        for (distance in inputs) {
            dial += distance
            while (dial > 99) dial -= 100
            while (dial < 0) dial += 100
            if (dial == 0) sum++
        }

        return SolutionResult.Success(sum)
    }

    override fun part2(input: SolutionInput): SolutionResult {
        val inputs = input.parse()

        // NOTE: Dial starts by pointing at 50
        var dial = 50
        var sum = 0

        for (distance in inputs) {
            val rot = distance / 100
            val rem = distance % 100

            sum += rot.absoluteValue

            when {
                dial != 0 && dial + rem <= 0 -> sum++
                dial + rem >= 100 -> sum++
            }

            dial = (dial + rem + 100) % 100
        }

        return SolutionResult.Success(sum)
    }
}
