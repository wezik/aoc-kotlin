package app.wezik.aoc2024.solution

import app.wezik.aoc2024.solution.days.*

class StaticSolverSelector {

    data class SolverSource(val solver: Solver, val path: String)

    private val solvers = mapOf(
        "1" to SolverSource(Day1Solver(), "inputs/day1"),
        "2" to SolverSource(Day2Solver(), "inputs/day2"),
        "3" to SolverSource(Day3Solver(), "inputs/day3"),
        "4" to SolverSource(Day4Solver(), "inputs/day4"),
        "5" to SolverSource(Day5Solver(), "inputs/day5"),
        "6" to SolverSource(Day6Solver(), "inputs/day6"),
        "7" to SolverSource(Day7Solver(), "inputs/day7"),
    )

    fun select(day: String): SolverSource {
        return solvers[day]?.also { println("Selected solver for day $day") }
            ?: throw IllegalArgumentException("Day $day not found")
    }

    fun selectAll(): List<SolverSource> {
        return solvers.toSortedMap().map { it.key }.map { select(it) }
    }

}
