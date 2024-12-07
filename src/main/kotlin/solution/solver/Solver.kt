package org.example.solution.solver

import org.example.solution.time
import org.example.solution.toNanoDuration
import kotlin.time.Duration

data class TimeWrapper(val result: String, val time: Duration)
data class SolutionWrapper(val part1: TimeWrapper, val part2: TimeWrapper)

interface Solver {
    fun solve(input: List<String>): SolutionWrapper {
        var part1Solution = ""
        val part1Time = time {
            part1Solution = part1(input)
        }.toNanoDuration()

        var part2Solution = ""
        val part2Time = time {
            part2Solution = part2(input)
        }.toNanoDuration()

        return SolutionWrapper(
            TimeWrapper(part1Solution, part1Time),
            TimeWrapper(part2Solution, part2Time)
        )
    }

    fun part1(input: List<String>): String
    fun part2(input: List<String>): String

}
