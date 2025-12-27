package app.wezik.aoc.solutions.y2025

import app.wezik.aoc.domain.Solution
import app.wezik.aoc.domain.SolutionInput
import app.wezik.aoc.domain.SolutionResult

import kotlin.concurrent.thread

object Day09 : Solution() {

    private fun SolutionInput.parse() = this.lines.map { 
        it.split(",").map { value -> value.toInt() }.let { (x, y) -> x to y }
    }

    override fun part1(input: SolutionInput): SolutionResult {
        val positions = input.parse()

        val candidates = mutableListOf<Pair<Int, Int>>()
        for (i in 0 until positions.size) {
            for (j in i + 1 until positions.size) {
                candidates += i to j
            }
        }

        var area = 0L
        for ((aIndex, bIndex) in candidates) {
            val (aX, aY) = positions[aIndex]
            val (bX, bY) = positions[bIndex]

            val (left, right) = listOf(aX, bX).sorted()
            val (top, bottom) = listOf(aY, bY).sorted()

            val candidateArea = (right - left + 1L) * (bottom - top + 1L)
            area = maxOf(candidateArea, area)
        }

        return SolutionResult.Success(area)
    }

    override fun part2(input: SolutionInput): SolutionResult {
        val positions = input.parse()

        // --- coordinate compression ---
        val (xs, ys) = positions.unzip().let { (xs, ys) -> xs.toSet().toList().sorted() to ys.toSet().toList().sorted() }

        for (i in 0 until xs.size - 1) {
            val j = i + 1
            val a = xs[i]
            val b = xs[j]
        }

        for (i in 0 until ys.size - 1) {
            val j = i + 1
            val a = ys[i]
            val b = ys[j]
        }

        val grid = Array(ys.size * 2 - 1) { BooleanArray(xs.size * 2 - 1) { false } }

        for (i in 0 until positions.size) {
            val (aX, aY) = positions[i]
            val (bX, bY) = positions[(i + 1) % positions.size]

            val (left, right) = listOf(xs.indexOf(aX) * 2, xs.indexOf(bX) * 2).sorted()
            val (top, bottom) = listOf(ys.indexOf(aY) * 2, ys.indexOf(bY) * 2).sorted()

            for (cx in left..right) {
                for (cy in top..bottom) {
                    grid[cy][cx] = true
                }
            }
        }

        // --- flood fill outside of the border ---
        val queue = ArrayDeque<Pair<Int, Int>>()
        val seen = mutableSetOf<Pair<Int, Int>>()
        val outside = mutableSetOf<Pair<Int, Int>>()

        queue.add(-1 to -1)
        while (queue.isNotEmpty()) {
            val (cx, cy) = queue.removeFirst()
            seen += cx to cy

            if (cx < 0 || cy < 0 || cy >= grid.size || cx >= grid[cy].size ) {
                // tollerate border
            } else if (!grid[cy][cx]) {
                outside += cx to cy
            } else {
                continue
            }

            for (dx in -1..1) {
                for (dy in -1..1) {
                    if (dx == 0 && dy == 0) continue
                    val nx = cx + dx
                    val ny = cy + dy
                    if (nx in -1..grid.size && ny in -1..grid[0].size && nx to ny !in seen && nx to ny !in queue) {
                        queue += nx to ny
                    }
                }
            }

        }

        // --- fill in inside of the border by inverting flood fill ---
        for (y in 0 until grid.size) {
            for (x in 0 until grid[y].size) {
                if (x to y !in outside) {
                    grid[y][x] = true
                }
            }
        }

        // --- check areas for each candidate of squares while checking if each position is inside the border ---
        val candidates = mutableListOf<Pair<Int, Int>>()
        for (i in 0 until positions.size) {
            for (j in i + 1 until positions.size) {
                candidates += i to j
            }
        }

        var area = 0L
        outer@ for ((aIndex, bIndex) in candidates) {
            val (aX, aY) = positions[aIndex]
            val (bX, bY) = positions[bIndex]

            val (left, right) = listOf(xs.indexOf(aX) * 2, xs.indexOf(bX) * 2).sorted()
            val (top, bottom) = listOf(ys.indexOf(aY) * 2, ys.indexOf(bY) * 2).sorted()

            for (cx in left..right) {
                for (cy in top..bottom) {
                    if (!grid[cy][cx]) continue@outer
                }
            }


            val (leftX, rightX) = listOf(aX, bX).sorted()
            val (topY, bottomY) = listOf(bY, aY).sorted()
            area = maxOf(area, (rightX - leftX + 1L) * (bottomY - topY + 1L))
        }

        return SolutionResult.Success(area)
    }
}
