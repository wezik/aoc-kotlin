package app.wezik.aoc.solutions.y2025

import app.wezik.aoc.domain.Solution
import app.wezik.aoc.domain.SolutionInput
import app.wezik.aoc.domain.SolutionResult
import app.wezik.aoc.domain.SolutionResult.NotImplemented
import kotlin.math.abs
import kotlin.math.absoluteValue

object Day07 : Solution() {

    override fun part1(input: SolutionInput): SolutionResult {
        val activeBeamColumns = mutableSetOf<Int>()

        input.lines.first().let { line ->
            line.indexOf('S').let { startIndex -> activeBeamColumns.add(startIndex) }
        }

        var totalCollisions = 0

        for (y in 1..<input.lines.size) {
            val line = input.lines[y]
            val collisions = mutableListOf<Int>()
            for (activeColumn in activeBeamColumns) {
                if (line[activeColumn] == '^') collisions.add(activeColumn)
            }

            for (collision in collisions) {
                activeBeamColumns.remove(collision)
                for (newColumn in listOf(collision - 1, collision + 1)) activeBeamColumns.add(newColumn)
            }

            totalCollisions += collisions.size
        }

        return SolutionResult.Success(totalCollisions)
    }

    override fun part2(input: SolutionInput): SolutionResult {
        val cache = mutableMapOf<Pair<Int, Int>, Long>()
        fun solve(pos: Pair<Int, Int>): Long {
            return cache.getOrPut(pos) {
                val (x, y) = pos;
                var timelines = 1L

                for (row in y..<input.lines.size) {
                    if (input.lines[row][x] == '^') {
                        timelines = solve(x - 1 to row) + solve(x + 1 to row)
                        break
                    }
                }

                timelines
            }
        }

        val start = input.lines.first().indexOf('S') to 0
        return SolutionResult.Success(solve(start))
    }
}
