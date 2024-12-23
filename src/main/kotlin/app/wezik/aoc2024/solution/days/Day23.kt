package app.wezik.aoc2024.solution.days

import app.wezik.aoc2024.solution.Solver

class Day23 : Solver {

    override fun part1(input: List<String>): String {
        val connections = HashMap<String, MutableSet<String>>()
        input.forEach {
            val (a, b) = it.split('-')
            connections.getOrPut(a) { mutableSetOf() } += b
            connections.getOrPut(b) { mutableSetOf() } += a
        }

        val sets = mutableSetOf<List<String>>()
        for (a in connections.keys) {
            for (b in connections[a]!!) {
                for (c in connections[b]!!) {
                    if (a != c && a in connections[c]!!) {
                        val triple = mutableSetOf(a, b, c)
                        sets += triple.sorted()
                    }
                }
            }
        }

        var total = 0
        outer@ for (con in sets) {
            for (entry in con) {
                if (entry.first() == 't') {
                    total++
                    continue@outer
                }
            }
        }

        return total.toString()
    }

    override fun part2(input: List<String>): String {
        val connections = HashMap<String, MutableSet<String>>()
        input.forEach {
            val (a, b) = it.split('-')
            connections.getOrPut(a) { mutableSetOf() } += b
            connections.getOrPut(b) { mutableSetOf() } += a
        }

        val sets = mutableSetOf<List<String>>()
        fun fillSets(src: String, acc: Set<String>) {
            val key = acc.sorted()
            if (key in sets) return
            sets += key
            outer@ for (candidate in connections[src]!!) {
                if (candidate in acc) continue
                // compare sets
                for (a in acc) {
                    if (a !in connections[candidate]!!) continue@outer
                }
                fillSets(candidate, acc + candidate)
            }
        }

        for (cn in connections.keys) {
            fillSets(cn, setOf(cn))
        }

        return sets.maxBy { it.size }.joinToString(",")
    }

}
