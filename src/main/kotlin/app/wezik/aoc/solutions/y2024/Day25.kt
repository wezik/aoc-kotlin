package app.wezik.aoc.solutions.y2024

import app.wezik.aoc.domain.Solution
import app.wezik.aoc.domain.SolutionInput

object Day25 : Solution (
    day = 25,
    year = 2024,
) {

    override fun part1(input: SolutionInput): String {
        val keys = mutableListOf<List<Int>>()
        val locks = mutableListOf<List<Int>>()
        input.file.readText().split("\n\n").forEach { section ->
            val lines = section.trim().split("\n")
            val heights = mutableListOf<Int>()
            for (i in 0 until lines.first().length) {
                val str = lines.map { it[i] }.joinToString("")
                heights += str.count { it == '#' } - 1
            }
            if (lines.first().first() == '#') locks += heights else keys += heights
        }

        var result = 0
        for (lock in locks) {
            outer@ for (key in keys) {
                for (i in 0 until key.size) {
                    if (lock[i] + key[i] > 5) continue@outer
                }
                result++

            }
        }
        return "$result"
    }
    // fun part2(input: SolutionInput) = "It's a secret! You can't find it! :>"

}
