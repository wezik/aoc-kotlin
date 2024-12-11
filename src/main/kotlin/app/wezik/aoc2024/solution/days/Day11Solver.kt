package app.wezik.aoc2024.solution.days

import app.wezik.aoc2024.solution.Solver

class Day11Solver : Solver {

    private data class Context(val stones: ArrayList<Long>) {
        var iterator = 0
    }

    private fun List<String>.parse(): Context {
        return Context(this.flatMap { line ->
            line.split(" ").map { it.toLong() }
        }.toMutableList() as ArrayList<Long>)
    }

    private enum class Rule {
        TO_1,
        REPLACE_BY_2,
        MULTIPLY,
    }

    private fun Context.blink() {
        iterator = 0
        while (iterator < stones.size) {
            val (a, b) = applyRule()
            stones[iterator] = a
            if (b != null) {
                stones.add(iterator + 1, b)
                iterator++
            }
            iterator++
        }
    }

    private fun Context.applyRule(): Pair<Long, Long?> {
        val rule = getRule()
        return when (rule) {
            Rule.TO_1 -> 1L to null
            Rule.REPLACE_BY_2 -> {
                val value = stones[iterator].toString()
                val a = value.slice(0 until value.length / 2).toLong()
                val b = value.slice(value.length / 2 until value.length).toLong()
                a to b
            }

            Rule.MULTIPLY -> stones[iterator] * 2024L to null
        }
    }

    private fun Context.getRule(): Rule {
        val value = stones[iterator]
        return when {
            value == 0L -> Rule.TO_1
            value.toString().length % 2 == 0 -> Rule.REPLACE_BY_2
            else -> Rule.MULTIPLY
        }
    }

    override fun part1(input: List<String>): String {
        val input = input.parse()
        (1..25).forEach {
            input.blink()
        }
        return input.stones.size.toString()
    }

    override fun part2(input: List<String>): String {
        return ""
    }
}
