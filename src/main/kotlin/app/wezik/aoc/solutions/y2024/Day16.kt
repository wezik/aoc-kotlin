package app.wezik.aoc.y2024

import app.wezik.aoc.Day
import java.io.File
import java.util.PriorityQueue

class Day16 : Day {

    private operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>) = first + other.first to second + other.second
    private data class QueueEntry(val cost: Int, val position: Pair<Int, Int>, val direction: Pair<Int, Int>)

    override fun part1(input: File): String {
        val input = input.readLines()
        // start and end are the same on every input
        var start = 1 to input.size - 2
        var end = input.first().length - 2 to 1

        // prepare the grid
        val grid = List(input.size) { BooleanArray(input.first().length) }
        for (row in 0 until input.size) {
            for (col in 0 until input[row].length) {
                if (input[row][col] != '#') grid[row][col] = true
            }
        }

        // priority queue based Dijkstra
        val startEntry = QueueEntry(cost = 0, position = start, direction = 1 to 0)
        val queue = PriorityQueue<QueueEntry>(compareBy { it.cost }).apply { add(startEntry) }
        val seen = mutableSetOf(startEntry.position to startEntry.direction)

        var result: Int? = null
        while (queue.isNotEmpty() && result == null) {
            val entry = queue.poll()
            // adding to seen only after processing
            seen.add(entry.position to entry.direction)
            if (entry.position == end) result = entry.cost

            // next entries
            val (dx, dy) = entry.direction
            val ahead = QueueEntry(entry.cost + 1, entry.position + (dx to dy), dx to dy)
            val right = QueueEntry(entry.cost + 1000, entry.position, -dy to dx)
            val left = QueueEntry(entry.cost + 1000, entry.position, dy to -dx)

            // processing loop
            val candidates = listOf(ahead, right, left)
            for (next in candidates) {
                val (nx, ny) = next.position
                if (!grid[ny][nx]) continue
                if (next.position to next.direction in seen) continue
                queue.add(next)
            }
        }

        return result.toString()
    }

    override fun part2(input: File): String {
        val input = input.readLines()
        // start and end are the same on every input
        var start = 1 to input.size - 2
        var end = input.first().length - 2 to 1

        // prepare the grid
        val grid = List(input.size) { BooleanArray(input.first().length) }
        for (row in 0 until input.size) {
            for (col in 0 until input[row].length) {
                if (input[row][col] != '#') grid[row][col] = true
            }
        }

        // priority queue based Dijkstra
        val startEntry = QueueEntry(cost = 0, position = start, direction = 1 to 0)
        val queue = PriorityQueue<QueueEntry>(compareBy { it.cost }).apply { add(startEntry) }
        val endStates = HashSet<QueueEntry>()

        // origin tracking
        val backtrack = HashMap<QueueEntry, MutableSet<QueueEntry>?>()
        val seenCost = mutableMapOf((startEntry.position to startEntry.direction) to 0)
        var bestCost = Int.MAX_VALUE

        while (queue.isNotEmpty()) {
            val entry = queue.poll()
            // if current entry is worse or present in seen, skip
            if (entry.cost > (seenCost[entry.position to entry.direction] ?: Int.MAX_VALUE)) continue
            // at the end pull in all valid end states
            if (entry.position == end) {
                if (entry.cost > bestCost) break
                bestCost = entry.cost
                endStates.add(entry)
            }

            // next entries
            val (dx, dy) = entry.direction
            val ahead = QueueEntry(entry.cost + 1, entry.position + (dx to dy), dx to dy)
            val right = QueueEntry(entry.cost + 1000, entry.position, -dy to dx)
            val left = QueueEntry(entry.cost + 1000, entry.position, dy to -dx)

            // processing loop
            val candidates = listOf(ahead, right, left)
            for (next in candidates) {
                val (nx, ny) = next.position
                if (!grid[ny][nx]) continue

                // on lower cost we are purging backtracking map and updating the cost
                val lowest = seenCost[(nx to ny) to next.direction] ?: Int.MAX_VALUE
                if (next.cost > lowest) continue
                if (next.cost < lowest) {
                    backtrack[next] = mutableSetOf()
                    seenCost[next.position to next.direction] = next.cost
                }

                queue.add(next)
                backtrack[next]!!.add(entry)
            }
        }

        // track back to starting point
        val states = ArrayDeque<QueueEntry>(endStates)
        val seen = HashSet<QueueEntry>(endStates)

        while (states.isNotEmpty()) {
            val entry = states.removeFirst()
            val origins = backtrack[entry] ?: continue
            for (origin in origins) {
                if (origin in seen) continue
                seen.add(origin)
                states.add(origin)
            }
        }

        return seen.distinctBy { it.position }.size.toString()
    }

}
