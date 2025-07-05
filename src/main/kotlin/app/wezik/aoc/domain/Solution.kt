package app.wezik.aoc.domain

import java.io.File

data class Solution(
    val day: Int,
    val year: Int,
    val part1: SolutionPart?,
    val part2: SolutionPart?
)

typealias SolutionPart = (File) -> String
