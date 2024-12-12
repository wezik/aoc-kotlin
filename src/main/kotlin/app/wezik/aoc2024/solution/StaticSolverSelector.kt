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
        DAY11(Day11Solver(), "inputs/day11"),
        DAY12(Day12Solver(), "inputs/day12"),
        DAY13(Day13Solver(), "inputs/day13"),
        DAY14(Day14Solver(), "inputs/day14"),
        DAY15(Day15Solver(), "inputs/day15"),
        DAY16(Day16Solver(), "inputs/day16"),
        DAY17(Day17Solver(), "inputs/day17"),
        DAY18(Day18Solver(), "inputs/day18"),
        DAY19(Day19Solver(), "inputs/day19"),
        DAY20(Day20Solver(), "inputs/day20"),
        DAY21(Day21Solver(), "inputs/day21"),
        DAY22(Day22Solver(), "inputs/day22"),
        DAY23(Day23Solver(), "inputs/day23"),
        DAY24(Day24Solver(), "inputs/day24"),
        DAY25(Day25Solver(), "inputs/day25"),
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
