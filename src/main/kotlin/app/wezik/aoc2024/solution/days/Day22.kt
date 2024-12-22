package app.wezik.aoc2024.solution.days

import app.wezik.aoc2024.solution.Solver

class Day22 : Solver {

    override fun part1(input: List<String>): String {
        return input.map { line ->
            (0 until 2000).fold(line.toLong()) { acc, _ ->
                var acc = acc xor (acc * 64) % 16777216
                acc = acc xor (acc / 32) % 16777216
                acc xor (acc * 2048) % 16777216
            }
        }.sum().toString()
    }

    override fun part2(input: List<String>): String {
        val result = mutableMapOf<List<Long>, Long>()
        input.forEach { line ->

            val buyer = mutableListOf(line.toLong() % 10)
            (0 until 2000).fold(line.toLong()) { acc, _ ->
                var acc = acc xor (acc * 64) % 16777216
                acc = acc xor (acc / 32) % 16777216
                (acc xor (acc * 2048) % 16777216).also { buyer += it % 10 }
            }

            val seen = mutableSetOf<List<Long>>()
            (0 until buyer.size - 4).forEach { i ->
                val sequence = (0 until 4).map { buyer[i + it + 1] - buyer[i + it] }.toList()
                if (sequence in seen) return@forEach
                seen += sequence
                if (sequence !in result) result[sequence] = 0
                result[sequence] = result[sequence]!! + buyer[i + 4]
            }

        }
        return result.values.max().toString()
    }

}
