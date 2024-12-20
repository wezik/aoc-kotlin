package app.wezik.aoc2024.solution.days

import app.wezik.aoc2024.solution.Solver

class Day20 : Solver {

    override fun part1(input: List<String>) = part1(input, 100)
    fun part1(input: List<String>, threshold: Int): String {
        val height = input.size
        val width = input[0].length
        val grid = Array(height) { BooleanArray(width) }

        fun Pair<Int, Int>.isValid() = first in 0 until width && second in 0 until height

        var start = -1 to -1
        for (y in 0 until height) {
            for (x in 0 until width) {
                if (input[y][x] == 'S') {
                    start = x to y
                } else if (input[y][x] == '#') {
                    grid[y][x] = true
                }
            }
        }

        // BFS
        val seen = Array(height) { IntArray(width) { -1 } }.apply {
            val (x, y) = start
            this[y][x] = 0
        }
        val queue = ArrayDeque(listOf(start))

        while (queue.isNotEmpty()) {
            // current (x, y)
            val (x, y) = queue.removeFirst()
            // candidate (cx, cy)
            for ((cx, cy) in listOf(x - 1 to y, x + 1 to y, x to y - 1, x to y + 1)) {
                if (!(cx to cy).isValid()) continue
                if (grid[cy][cx]) continue
                if (seen[cy][cx] != -1) continue
                seen[cy][cx] = seen[y][x] + 1
                queue.add(cx to cy)
            }
        }

        // Note difference needs to be at least 102 picoseconds since it takes 2 to move by itself
        var count = 0
        for (y in 0 until height) {
            for (x in 0 until width) {
                if (grid[y][x]) continue
                for ((nx, ny) in listOf(x to y - 2, x + 2 to y, x to y + 2, x - 2 to y)) {
                    if (nx !in 0 until width || ny !in 0 until height) continue
                    if (grid[ny][nx]) continue
                    if (seen[y][x] - seen[ny][nx] >= threshold + 2) count++
                }
            }
        }

        return count.toString()
    }

    override fun part2(input: List<String>) = part2(input, 100)
    fun part2(input: List<String>, threshold: Int): String {
        val height = input.size
        val width = input[0].length
        val grid = Array(height) { BooleanArray(width) }

        fun Pair<Int, Int>.isValid() = first in 0 until width && second in 0 until height

        var start = -1 to -1
        for (y in 0 until height) {
            for (x in 0 until width) {
                if (input[y][x] == 'S') {
                    start = x to y
                } else if (input[y][x] == '#') {
                    grid[y][x] = true
                }
            }
        }

        // BFS
        val seen = Array(height) { IntArray(width) { -1 } }.apply {
            val (x, y) = start
            this[y][x] = 0
        }
        val queue = ArrayDeque(listOf(start))

        while (queue.isNotEmpty()) {
            // current (x, y)
            val (x, y) = queue.removeFirst()
            // candidate (cx, cy)
            for ((cx, cy) in listOf(x - 1 to y, x + 1 to y, x to y - 1, x to y + 1)) {
                if (!(cx to cy).isValid()) continue
                if (grid[cy][cx]) continue
                if (seen[cy][cx] != -1) continue
                seen[cy][cx] = seen[y][x] + 1
                queue.add(cx to cy)
            }
        }

        var count = 0
        for (y in 0 until height) {
            for (x in 0 until width) {
                if (grid[y][x]) continue
                for (radius in 2..20) {
                    // radius (rx, ry)
                    for (ry in 0..radius) {
                        val rx = radius - ry
                        for ((nx, ny) in setOf(
                            x + rx to y + ry,
                            x - rx to y + ry,
                            x + rx to y - ry,
                            x - rx to y - ry,
                        )) {
                            if (!(nx to ny).isValid()) continue
                            if (grid[ny][nx]) continue
                            if (seen[y][x] - seen[ny][nx] >= threshold + radius) count++
                        }
                    }
                }
            }
        }
        return count.toString()
    }
}
