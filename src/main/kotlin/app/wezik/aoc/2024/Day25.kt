package app.wezik.aoc.y2024

import app.wezik.aoc.Day
import java.io.File

class Day25 : Day {

    override fun part1(input: File): String {
        val keys = mutableListOf<List<Int>>()
        val locks = mutableListOf<List<Int>>()
        input.readText().split("\n\n").forEach { section ->
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
    override fun part2(input: File) = ""

}
