package org.example.solution.solver.days

import org.example.solution.solver.Solver
import kotlin.math.abs

class Day2Solver : Solver {

    private data class Report(var levels: List<Int>)

    private fun List<String>.toReports(): List<Report> {
        return this.map { it.toReport() }
    }

    private fun String.toReport(): Report {
        return this.split(' ').map { it.toInt() }.let { Report(it) }
    }

    override fun part1(input: List<String>) = input.toReports().filter {
        isSafe(it.levels, false)
    }.size.toString()

    override fun part2(input: List<String>) = input.toReports().filter {
        isSafe(it.levels, true)
    }.size.toString()

    private fun isSafe(levels: List<Int>, tolerance: Boolean): Boolean {
        // Calculate if the numbers are growing or shrinking
        val deviation = (0..2).map { if (levels[it] < levels[it + 1]) 1 else -1 }.sum()

        var a = levels[0]
        for (i in 1 until levels.size) {
            var b = levels[i]

            if (isValid(a, b, deviation)) {
                a = b
                continue
            } else if (tolerance) {
                // Yes I am brute forcing this, the conditions are a mess for my head;
                // tldr: retries -1, 0, 1 offsets from the current index
                (-1..1).map { offset ->
                    i + offset
                }.forEach { tryIndex ->
                    val newLevels = levels.toMutableList()
                    newLevels.removeAt(tryIndex)
                    if (isSafe(newLevels, false)) return true
                }
            }
            // If we reach this point, it failed validation and retries
            return false
        }
        // If we reach this point all levels are valid
        return true
    }

    private fun isValid(a: Int, b: Int, deviation: Int): Boolean {
        val isInRange = abs(a - b) in (1..3)
        if (!isInRange) return false
        val isFollowingDirection = when {
            (b > a && deviation > 0) -> true
            (b < a && deviation < 0) -> true
            else -> false
        }
        return isFollowingDirection
    }
}
