package app.wezik.aoc.y2015

import app.wezik.aoc.Day
import java.io.File

class Day03 : Day {

    operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>) = first + other.first to second + other.second

    override fun part1(input: File): String {
        var str = input.readText().replace("\n", "")
        var pos = 0 to 0
        val seen = mutableSetOf(pos)
        for (dir in str) {
            when (dir) {
                '^' -> pos += -1 to 0
                'v' -> pos += 1 to 0
                '>' -> pos += 0 to 1
                '<' -> pos += 0 to -1
            }
            seen.add(pos)
        }
        return seen.size.toString()
    }

    override fun part2(input: File): String {
        var str = input.readText().replace("\n", "")

        fun Pair<Int, Int>.move(dir: Char): Pair<Int, Int> {
            var pos = this
            when (dir) {
                '^' -> pos += -1 to 0
                'v' -> pos += 1 to 0
                '>' -> pos += 0 to 1
                '<' -> pos += 0 to -1
                else -> error("Invalid direction: $dir")
            }
            return pos
        }

        var santa = 0 to 0
        var robot = 0 to 0
        val seen = mutableSetOf(santa)

        for ((i, dir) in str.withIndex()) {
            if (i % 2 == 0) {
                santa = santa.move(dir)
                seen.add(santa)
            } else {
                robot = robot.move(dir)
                seen.add(robot)
            }
        }
        return seen.size.toString()
    }
}
