package app.wezik.aoc.solutions.y2025

import app.wezik.aoc.domain.Solution
import app.wezik.aoc.domain.SolutionInput
import app.wezik.aoc.domain.SolutionResult

object Day09 : Solution() {

    override fun part1(input: SolutionInput): SolutionResult {
        val positions = input.lines
            .map { 
                it.split(",")
                .map { value -> value.toInt() }
                .let { (x, y) -> x to y } 
            }

        val candidates = mutableListOf<Pair<Int, Int>>()
        for (i in 0 until positions.size) {
            for (j in i + 1 until positions.size) {
                candidates += i to j
            }
        }

        var area = 0L
        for ((aIndex, bIndex) in candidates) {
            val (aX, aY) = positions[aIndex]
            val (bX, bY) = positions[bIndex]

            val (left, right) = listOf(aX, bX).sorted()
            val (top, bottom) = listOf(aY, bY).sorted()

            val candidateArea = (right - left + 1L) * (bottom - top + 1L)
            area = maxOf(candidateArea, area)
        }

        return SolutionResult.Success(area)
    }

    override fun part2(input: SolutionInput): SolutionResult {
        return SolutionResult.Success(0)
    }
}
