package app.wezik.aoc.domain

import java.io.File

// NOTE: wrapper class that should make it easier to extend later
open class SolutionInput(val file: File)

open class Solution(val day: Int, val year: Int) {
    open fun part1(input: SolutionInput) = ""
    open fun part2(input: SolutionInput) = ""
}
