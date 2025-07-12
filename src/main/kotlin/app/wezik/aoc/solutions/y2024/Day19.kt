package app.wezik.aoc.solutions.y2024

import app.wezik.aoc.domain.Solution
import app.wezik.aoc.domain.SolutionInput
import app.wezik.aoc.domain.SolutionResult
import app.wezik.aoc.domain.SolutionResult.Success
import kotlin.math.min

object Day19 : Solution() {

    override fun part1(input: SolutionInput): SolutionResult {
        val input = input.lines
        val patterns = input[0].split(", ").toSet()
        val maxLength = patterns.maxOf { it.length }

        val cache = mutableMapOf("" to true)
        fun canObtain(design: String): Boolean {
            if (design in cache) return cache[design]!!
            for (i in 0..min(design.length, maxLength)) {
                if (design.substring(0, i) in patterns && canObtain(design.substring(i))) {
                    cache[design] = true
                    return true
                }
            }
            cache[design] = false
            return false
        }

        return Success(input.drop(2).filter { canObtain(it) }.count().toString())
    }

    override fun part2(input: SolutionInput): SolutionResult {
        val input = input.lines
        val patterns = input[0].split(", ").toSet()
        val maxLength = patterns.maxOf { it.length }

        val cache = mutableMapOf("" to 1L)
        fun possibilities(design: String): Long {
            if (design in cache) return cache[design]!!
            var count = 0L
            for (i in 0..min(design.length, maxLength)) {
                if (design.substring(0, i) in patterns) {
                    count += possibilities(design.substring(i))
                }
            }
            cache[design] = count
            return count
        }

        return Success(input.drop(2).map { possibilities(it) }.sum().toString())
    }

}
