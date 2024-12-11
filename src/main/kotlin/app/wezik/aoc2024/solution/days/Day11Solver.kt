package app.wezik.aoc2024.solution.days

import app.wezik.aoc2024.solution.Solver

class Day11Solver : Solver {

    private data class StoneEntry(val value: Long, val blinks: Int)

    private data class Context(val stones: List<Long>) {
        // Cache storing result for stone with x blinks
        val cache = mutableMapOf<StoneEntry, Long>()
    }

    private fun List<String>.parse() = Context(this.flatMap { it.split(' ').map { it.toLong() } })

    private fun Context.count(stoneEntry: StoneEntry): Long {
        val next = getNextEntry(stoneEntry)
        if (next.first == null) return 1
        if (next.second == null) return cache.getOrPut(next.first!!) { this.count(next.first!!) }
        val a = cache.getOrPut(next.first!!) { this.count(next.first!!) }
        val b = cache.getOrPut(next.second!!) { this.count(next.second!!) }
        return a + b
    }

    private fun getNextEntry(stoneEntry: StoneEntry): Pair<StoneEntry?, StoneEntry?> {
        if (stoneEntry.blinks == 0) return null to null
        if (stoneEntry.value == 0L) return StoneEntry(1, stoneEntry.blinks - 1) to null
        val str = stoneEntry.value.toString()
        if (str.length % 2 == 0) {
            val a = str.slice(0 until str.length / 2).toLong()
            val b = str.slice(str.length / 2 until str.length).toLong()
            return StoneEntry(a, stoneEntry.blinks - 1) to StoneEntry(b, stoneEntry.blinks - 1)
        }
        return StoneEntry(stoneEntry.value * 2024, stoneEntry.blinks - 1) to null
    }

    override fun part1(input: List<String>): String {
        val ctx = input.parse()
        return ctx.stones.map { ctx.count(StoneEntry(it, 25)) }.sum().toString()
    }

    override fun part2(input: List<String>): String {
        val ctx = input.parse()
        return ctx.stones.map { ctx.count(StoneEntry(it, 75)) }.sum().toString()
    }
}
