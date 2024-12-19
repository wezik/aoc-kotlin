package app.wezik.aoc2024.solution

import app.wezik.aoc2024.solution.days.*

class StaticSolverSelector {

    enum class SolverSource(val solver: Solver, val path: String) {
        DAY01(Day01(), "inputs/day01"),
        DAY02(Day02(), "inputs/day02"),
        DAY03(Day03(), "inputs/day03"),
        DAY04(Day04(), "inputs/day04"),
        DAY05(Day05(), "inputs/day05"),
        DAY06(Day06(), "inputs/day06"),
        DAY07(Day07(), "inputs/day07"),
        DAY08(Day08(), "inputs/day08"),
        DAY09(Day09(), "inputs/day09"),
        DAY10(Day10(), "inputs/day10"),
        DAY11(Day11(), "inputs/day11"),
        DAY12(Day12(), "inputs/day12"),
        DAY13(Day13(), "inputs/day13"),
        DAY14(Day14(), "inputs/day14"),
        DAY15(Day15(), "inputs/day15"),
        DAY16(Day16(), "inputs/day16"),
        DAY17(Day17(), "inputs/day17"),
        DAY18(Day18(), "inputs/day18"),
        DAY19(Day19(), "inputs/day19"),
        DAY20(Day20(), "inputs/day20"),
        DAY21(Day21(), "inputs/day21"),
        DAY22(Day22(), "inputs/day22"),
        DAY23(Day23(), "inputs/day23"),
        DAY24(Day24(), "inputs/day24"),
        DAY25(Day25(), "inputs/day25"),
    }

    fun select(day: Int): SolverSource {
        val entries = SolverSource.entries.getOrNull(day - 1)
        if (entries == null) error("Day $day does not exist")
        return entries
    }

    fun selectAll(): List<SolverSource> {
        return SolverSource.entries
    }

}
