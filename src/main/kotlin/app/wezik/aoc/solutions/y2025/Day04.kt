package app.wezik.aoc.solutions.y2025

import app.wezik.aoc.domain.Solution
import app.wezik.aoc.domain.SolutionInput
import app.wezik.aoc.domain.SolutionResult
import app.wezik.aoc.domain.SolutionResult.NotImplemented
import kotlin.math.abs
import kotlin.math.absoluteValue

object Day04 : Solution() {

    private fun genAdjacentPositions(x: Int, y: Int) = listOf(
        x - 1 to y - 1,
        x + 1 to y - 1,
        x to y - 1,

        x - 1 to y,
        x + 1 to y,

        x - 1 to y + 1,
        x + 1 to y + 1,
        x to y + 1,
    )

    override fun part1(input: SolutionInput): SolutionResult {
        var accessible = 0

        for (y in 0 until input.lines.size) {
            for (x in 0 until input.lines[y].length) {
                if (input.lines[y][x] != '@') continue

                var sum = 0
                for ((nx, ny) in genAdjacentPositions(x, y)) {
                    if (nx < 0 || ny < 0 || ny >= input.lines.size || nx >= input.lines[ny].length) continue
                    if (input.lines[ny][nx] == '@') sum++
                }

                if (sum < 4) accessible++
            }
        }

        return SolutionResult.Success(accessible)
    }

    override fun part2(input: SolutionInput): SolutionResult {
        val accessible = mutableSetOf<Pair<Int, Int>>()
        var totalRemoved = 0
        val input = input.lines.map { it.map { it == '@' }.toMutableList() }

        while(true) {
            for (y in 0 until input.size) {
                for (x in 0 until input[y].size) {
                    if (!input[y][x]) continue

                    var sum = 0
                    for ((nx, ny) in genAdjacentPositions(x, y)) {
                        if (nx < 0 || ny < 0 || ny >= input.size || nx >= input[ny].size) continue
                        if (input[ny][nx]) sum++
                    }

                    if (sum < 4) accessible.add(x to y)
                }
            }

            if (accessible.isEmpty()) break

            accessible.forEach { (x, y) ->
                totalRemoved++
                input[y][x] = false
            }
            accessible.clear()
        }

        return SolutionResult.Success(totalRemoved)
    }
}
