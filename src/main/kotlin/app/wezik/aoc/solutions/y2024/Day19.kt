package app.wezik.aoc.y2024

import kotlin.math.min
import app.wezik.aoc.Day
import java.io.File

class Day19 : Day {

    override fun part1(input: File): String {
        val input = input.readLines()
        val patterns = input[0].split(", ").toSet()
        val maxLength = patterns.maxOf { it.length }

        val cache = mutableMapOf("" to true)
        fun canObtain(design: String): Boolean {
            if (design in cache) return cache[design]!!
            for (i in 0..min(design.length, maxLength)) {
                if (design.substring(0, i) in patterns && canObtain(design.substring(i))) {
                    cache[design] = true
                    return true
                }
            }
            cache[design] = false
            return false
        }

        return input.drop(2).filter { canObtain(it) }.count().toString()
    }

    override fun part2(input: File): String {
        val input = input.readLines()
        val patterns = input[0].split(", ").toSet()
        val maxLength = patterns.maxOf { it.length }

        val cache = mutableMapOf("" to 1L)
        fun possibilities(design: String): Long {
            if (design in cache) return cache[design]!!
            var count = 0L
            for (i in 0..min(design.length, maxLength)) {
                if (design.substring(0, i) in patterns) {
                    count += possibilities(design.substring(i))
                }
            }
            cache[design] = count
            return count
        }

        return input.drop(2).map { possibilities(it) }.sum().toString()
    }

}
