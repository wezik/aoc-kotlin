package app.wezik.aoc.y2024

import app.wezik.aoc.Day
import java.io.File

class Day18 : Day {

    private companion object {
        val DIRECTIONS = listOf(
            Pair(0, -1), // Up
            Pair(0, 1),  // Down
            Pair(-1, 0), // Left
            Pair(1, 0),  // Right
        )
    }

    private data class QueueEntry(val position: Pair<Int, Int>, val cost: Int)

    private fun getDistance(input: List<String>, cycles: Int, max: Int): Int {
        val badBytesInput = input.subList(0, cycles).map { it.split(",").map { it.toInt() }.let { (x, y) -> x to y } }
        val badBytes = List<BooleanArray>(max + 1) { BooleanArray(max + 1) }
        badBytesInput.forEach { (x, y) -> badBytes[y][x] = true }

        // BFS
        val queue = ArrayDeque<QueueEntry>().apply { add(QueueEntry(0 to 0, 0)) }
        queue.add(QueueEntry(0 to 0, 0))
        val seen = HashSet<Pair<Int, Int>>()

        while (queue.isNotEmpty()) {
            val (pos, dist) = queue.removeFirst()
            for ((dirX, dirY) in DIRECTIONS) {
                val (x, y) = pos
                val nextX = x + dirX
                val nextY = y + dirY
                // Out of bounds check
                if (nextX !in 0..max || nextY !in 0..max) continue
                // Bad byte check
                if (badBytes[nextY][nextX]) continue
                // Seen check
                if (seen.contains(nextX to nextY)) continue
                // End check
                if (nextX == max && nextY == max) return dist + 1
                seen.add(nextX to nextY)
                queue.add(QueueEntry(nextX to nextY, dist + 1))
            }
        }

        return -1
    }

    override fun part1(input: File) = part1(input.readLines(), 1024)
    fun part1(input: List<String>, cycles: Int): String {
        var max = 0
        for (line in input) {
            line.split(",").map { it.toInt() }.let { (x, y) ->
                if (x > max) max = x
                if (y > max) max = y
            }
        }
        return getDistance(input, cycles, max).toString()
    }

    override fun part2(input: File): String {
        val input = input.readLines()
        var max = 0
        for (line in input) {
            line.split(",").map { it.toInt() }.let { (x, y) ->
                if (x > max) max = x
                if (y > max) max = y
            }
        }

        // binary search
        var low = 0
        var high = input.size - 1
        while (low < high) {
            val mid = (low + high) / 2
            if (getDistance(input, mid + 1, max) >= 0) low = mid + 1
            else high = mid
        }

        return input[low]
    }

}
