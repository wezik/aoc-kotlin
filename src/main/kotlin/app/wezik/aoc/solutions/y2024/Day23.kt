package app.wezik.aoc.solutions.y2024

import app.wezik.aoc.domain.Solution
import app.wezik.aoc.domain.SolutionInput
import app.wezik.aoc.domain.SolutionResult
import app.wezik.aoc.domain.SolutionResult.Success

object Day23 : Solution() {

    override fun part1(input: SolutionInput): SolutionResult {
        val connections = HashMap<String, MutableSet<String>>()
        input.lines.forEach {
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

        return Success(total.toString())
    }

    override fun part2(input: SolutionInput): SolutionResult {
        val connections = HashMap<String, MutableSet<String>>()
        input.lines.forEach {
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

        return Success(sets.maxBy { it.size }.joinToString(","))
    }

}
