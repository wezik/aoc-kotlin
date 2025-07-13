package app.wezik.aoc.solutions.y2024

import app.wezik.aoc.domain.Solution
import app.wezik.aoc.domain.SolutionInput
import app.wezik.aoc.domain.SolutionResult.Success
import kotlin.math.abs

object Day01 : Solution() {

    override fun part1(input: SolutionInput) = Success (
        input.lines
            .map { it.split("   ").map { it.toInt() } } // parse into ints
            .map { it[0] to it[1] } // create pairs
            .unzip() // unzip pairs to lists
            .let { (l, r) -> l.sorted() to r.sorted() } // sort each list
            .let { (l, r) -> l.zip(r) } // zip lists together
            .sumOf { (v1, v2) -> abs(v1 - v2) } // sum differences
    )

    override fun part2(input: SolutionInput) = Success (
        input.lines
            .map { it.split("   ").map { it.toInt() } } // parse into ints
            .map { it[0] to it[1] } // create pairs
            .unzip() // unzip pairs to lists
            .let { (l, r) -> l.sorted() to r.sorted() } // sort each list
            .let { (l, r) -> l.sumOf { v -> v * r.count { it == v } } } // sum occurrences from left in the right
    )
}
