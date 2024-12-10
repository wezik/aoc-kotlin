package app.wezik.aoc2024.solution

import app.wezik.aoc2024.solution.days.*

class StaticSolverSelector {

    enum class SolverSource(val solver: Solver, val path: String) {
        DAY1(Day1Solver(), "inputs/day1"),
        DAY2(Day2Solver(), "inputs/day2"),
        DAY3(Day3Solver(), "inputs/day3"),
        DAY4(Day4Solver(), "inputs/day4"),
        DAY5(Day5Solver(), "inputs/day5"),
        DAY6(Day6Solver(), "inputs/day6"),
        DAY7(Day7Solver(), "inputs/day7"),
        DAY8(Day8Solver(), "inputs/day8"),
        DAY9(Day9Solver(), "inputs/day9"),
        DAY10(Day10Solver(), "inputs/day10"),
    }

    fun select(day: String): SolverSource {
        val i = day.toInt() - 1
        val entries = SolverSource.entries.getOrNull(i)
        if (entries == null) error("Day $day does not exist")
        return entries
    }

    fun selectAll(): List<SolverSource> {
        return SolverSource.entries
    }

}
