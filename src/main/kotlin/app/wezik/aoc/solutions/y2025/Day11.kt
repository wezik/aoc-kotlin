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
                paths++
                continue
            }

            for (next in connections[current]) {
                queue.add(next)
            }
        }

        return SolutionResult.Success(paths)
    }

    // NOTE: Unfortunately current runner structure does not allow for different inputs depending on the part of the puzzle so this will print incorrect results for test runs
    override fun part2(input: SolutionInput): SolutionResult {
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

        val start = points.indexOf("svr")
        val end = -1 // as it's not listed in the input as a point, it will be identifiable by -1
        val fft = points.indexOf("fft")
        val dac = points.indexOf("dac")

        val cache = Array(points.size) { LongArray(4) { -1 } }

        // mask 0 = none, 1 = fft, 2 = dac, 3 = both
        fun findOuts(index: Int, mask: Int): Long {
            val newMask = 
                mask or
                (if (index == fft) 1 else 0) or
                (if (index == dac) 2 else 0)

            if (index == end) return if (newMask == 3) 1 else 0

            if (cache[index][newMask] != -1L) return cache[index][newMask]

            var total = 0L
            for (value in connections[index]) {
                total += findOuts(value, newMask)
            }

            cache[index][newMask] = total
            return total
        }

        val paths = findOuts(start, 0)

        return SolutionResult.Success(paths)
    }
}
