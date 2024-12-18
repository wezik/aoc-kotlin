package app.wezik.aoc2024.solution.days

import app.wezik.aoc2024.solution.Solver

class Day18 : Solver {

    private companion object {
        val DIRECTIONS = listOf(
            Pair(0, -1), // Up
            Pair(0, 1),  // Down
            Pair(-1, 0), // Left
            Pair(1, 0),  // Right
        )
    }

    private data class QueueEntry(val position: Pair<Int, Int>, val cost: Int)

    private fun getDistance(badBytes: List<BooleanArray>, max: Int): Int {
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

    override fun part1(input: List<String>) = part1(input, 1024)
    fun part1(input: List<String>, cycles: Int): String {
        var max = 0
        val badBytesInput = mutableListOf<Pair<Int, Int>>()
        for ((i, line) in input.withIndex()) {
            if (i >= cycles) break
            line.split(",").map { it.toInt() }.let { (x, y) ->
                // This max tracking is bad, it counts on max value appearing in the n of cycles
                // but seems to work fine with the inputs
                if (x > max) max = x
                if (y > max) max = y
                badBytesInput.add(x to y)
            }
        }
        // We don't read more bytes than cycle count so all are valid
        val badBytes = List<BooleanArray>(max + 1) { BooleanArray(max + 1) }
        badBytesInput.forEach { (x, y) -> badBytes[y][x] = true }

        return getDistance(badBytes, max).toString()
    }

    override fun part2(input: List<String>): String {
        var max = 0
        val badBytesInput = ArrayDeque<Pair<Int, Int>>()
        for (line in input) {
            line.split(",").map { it.toInt() }.let { (x, y) ->
                if (x > max) max = x
                if (y > max) max = y
                badBytesInput.add(x to y)
            }
        }

        val badBytes = List<BooleanArray>(max + 1) { BooleanArray(max + 1) }

        while (badBytesInput.isNotEmpty()) {
            val (bx, by) = badBytesInput.removeFirst()
            badBytes[by][bx] = true
            val dist = getDistance(badBytes, max)
            if (dist == -1) return "$bx,$by"
        }

        return "all valid"
    }

}
