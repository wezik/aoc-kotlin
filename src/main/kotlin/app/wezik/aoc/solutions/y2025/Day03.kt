package app.wezik.aoc.solutions.y2025

import app.wezik.aoc.domain.Solution
import app.wezik.aoc.domain.SolutionInput
import app.wezik.aoc.domain.SolutionResult
import app.wezik.aoc.domain.SolutionResult.NotImplemented
import kotlin.math.abs
import kotlin.math.absoluteValue

object Day03 : Solution() {

    override fun part1(input: SolutionInput): SolutionResult {
        val banks = input.lines.map { line -> line.map { it.toString().toInt() }.toList() }

        var sum = 0
        for (bank in banks) {
            var maximumJoltage = 0
            for (i in 0 until bank.size) {
                for (j in i + 1 until bank.size) {
                    val joltage = bank[i] * 10 + bank[j]
                    if (joltage > maximumJoltage) maximumJoltage = joltage
                }
            }
            sum += maximumJoltage
        }

        return SolutionResult.Success(sum)
    }

    override fun part2(input: SolutionInput): SolutionResult {
        val banks = input.lines.map { line -> line.map { it.toString().toInt() }.toList() }

        // returns digit and leftover
        fun List<Int>.extractLargestWithPadding(padding: Int): Pair<Long, List<Int>> {
            // first trim the list to the valid digits for given padding
            val validRange = this.subList(0, this.size - padding)
            // find biggest digit
            val max = validRange.withIndex().maxBy { it.value }
            // trim to leftover values
            val leftover = this.subList(max.index + 1, this.size)

            // calculate decimal based on padding
            var decimal = 1L
            for (i in 0 until padding) decimal *= 10

            // return the biggest digit with modified decimal and leftover:w
            return max.value.toLong() * decimal to leftover
        }

        var sum = 0L
        for (bank in banks) {
            var leftover = bank
            var maxJoltage = 0L
            for (i in 11 downTo 0) {
                val (num, newLeftover) = leftover.extractLargestWithPadding(i)
                leftover = newLeftover
                maxJoltage += num
            }

            sum += maxJoltage
        }

        return SolutionResult.Success(sum)
    }
}
