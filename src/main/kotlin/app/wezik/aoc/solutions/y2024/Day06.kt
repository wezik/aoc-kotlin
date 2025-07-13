package app.wezik.aoc.solutions.y2024

import app.wezik.aoc.domain.Solution
import app.wezik.aoc.domain.SolutionInput
import app.wezik.aoc.domain.SolutionResult
import app.wezik.aoc.domain.SolutionResult.Success
import kotlin.math.abs

object Day06 : Solution() {

    // NOTE: this solution is hard to follow, mostly due to it being pretty slow unless heavily optimized
    // key optimizations done here are:
    // - (part2) using a pre-computed cache and jump map to skip already seen positions
    // - (part2) starting calculations from the position just before the candidate for a box
    // - using very simplified data structures to avoid allocations overhead (it is significant here)
    // it all reduced part2 runtime (on my setup) from ~900 ms to x ms

    // --- data structures ---

    private enum class Direction { UP, RIGHT, DOWN, LEFT }
    private data class ParseOutput(
        val grid: Array<BooleanArray>,
        var guard: Pair<Int, Int>,
    )

    // --- parsing ---

    private fun parse(input: SolutionInput): ParseOutput {
        val height = input.lines.size
        val width = input.lines.first().length
        val grid = Array(height) { BooleanArray(width) }
        var guard: Pair<Int, Int>? = null

        for (y in 0 until height) {
            for (x in 0 until width) {
                when (input.lines[y][x]) {
                    '#' -> grid[y][x] = true
                    '^' -> guard =  x to y
                }
            }
        }

        return ParseOutput(
            grid = grid,
            guard = guard!!,
        )
    }

    // --- util functions ---

    private fun Pair<Int, Int>.inBounds(width: Int, height: Int) = first in 0..<width && second in 0..<height
    private operator fun Pair<Int, Int>.plus(other: Direction) = when (other) {
        Direction.UP -> first to second - 1
        Direction.RIGHT -> first + 1 to second
        Direction.DOWN -> first to second + 1
        Direction.LEFT -> first - 1 to second
    }
    private fun Direction.rotate() = when (this) {
        Direction.UP -> Direction.RIGHT
        Direction.RIGHT -> Direction.DOWN
        Direction.DOWN -> Direction.LEFT
        Direction.LEFT -> Direction.UP
    }

    // --- solutions ---

    override fun part1(input: SolutionInput): SolutionResult {
        val height = input.lines.size
        val width = input.lines.first().length
        val (grid, guard) = parse(input)

        var (pos, dir) = guard to Direction.UP
        val seen = Array(height) { BooleanArray(width) }

        while (pos.inBounds(width, height)) {
            seen[pos.second][pos.first] = true
            var newPos = pos + dir

            while (grid.getOrNull(newPos.second)?.getOrNull(newPos.first) == true) {
                dir = dir.rotate()
                newPos = pos + dir
            }

            pos = newPos
        }

        val count = seen.sumOf { it.count { it } }
        return Success(count)
    }

    override fun part2(input: SolutionInput): SolutionResult {
        val height = input.lines.size
        val width = input.lines.first().length
        val grid = Array(height) { BooleanArray(width) }
        var guardOrigin = 0 to 0 to Direction.UP

        for (y in 0 until height) {
            for (x in 0 until width) {
                when (input.lines[y][x]) {
                    '#' -> grid[y][x] = true
                    '^' -> guardOrigin = x to y to Direction.UP
                }
            }
        }

        var (cachePos, cacheDir) = guardOrigin

        val cache = mutableListOf<Pair<Pair<Int, Int>, Direction>>()
        while (cachePos.inBounds(width, height)) {
            cache += cachePos to cacheDir
            var newPos = cachePos + cacheDir

            while (grid.getOrNull(newPos.second)?.getOrNull(newPos.first) == true) {
                cacheDir = cacheDir.rotate()
                newPos = cachePos // rotate in place
            }

            cachePos = newPos
        }

        var count = 0
        for (i in 1 until cache.size) {
            val (candidate, _) = cache[i]
            var (pos, dir) = cache[i - 1]

            if (candidate == guardOrigin.first) continue // skip original starting position
            if (candidate == pos) continue // skip if on top of guard
            val cachedSeen = cache.subList(0, i - 1)
            if (cachedSeen.any { (pos, _) -> pos == candidate }) continue // skip if already seen

            val seen = Array(height) { Array(width) { BooleanArray(4) } }
            // mark already seen positions up to this point
            for (s in cachedSeen) {
                seen[s.first.second][s.first.first][s.second.ordinal] = true
            }

            while (pos.inBounds(width, height)) {
                if (seen[pos.second][pos.first][dir.ordinal]) {
                    count++
                    break
                }

                seen[pos.second][pos.first][dir.ordinal] = true
                var newPos = pos + dir

                if (grid.getOrNull(newPos.second)?.getOrNull(newPos.first) == true || newPos == candidate) {
                    dir = dir.rotate()
                    newPos = pos // reset to previous position to check in place rotation
                }
                pos = newPos
            }
        }

        return Success(count)
    }
}
