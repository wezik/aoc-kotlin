package app.wezik.aoc.solutions.y2025

import app.wezik.aoc.domain.Solution
import app.wezik.aoc.domain.SolutionInput
import app.wezik.aoc.domain.SolutionResult
import java.util.BitSet

object Day11 : Solution() {

    override fun part1(input: SolutionInput): SolutionResult {

        val (points, connections) = input.lines.map { line ->
            val (point, connections) = line.split(": ")
            point to connections.split(" ")
        }.let { entries ->
            val points = entries.map { (point, _) -> point }.toSet().toList()
            val connections = List<MutableList<Int>>(points.size) { mutableListOf() }
            for ((point, connected) in entries) {
                val pointIndex = points.indexOf(point)
                connections[pointIndex].addAll(connected.map { points.indexOf(it) }.toList())
            }

            points to connections
        }

        val start = points.indexOf("you")
        val end = -1 // as it's not listed in the input as a point, it will be identifiable by -1

        var paths = 0
        val queue = ArrayDeque<Int>().apply { add(start) }

        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            if (current == end) {
                // purge the queue out of ends at once
                paths += queue.count { it == end }
                queue.removeAll { it == end }
                continue
            }

            for (next in connections[current]) {
                queue.add(next)
            }
        }

        return SolutionResult.Success(paths)
    }

    // override fun part2(input: SolutionInput): SolutionResult {
    //     return SolutionResult.Success(0)
    // }
}
