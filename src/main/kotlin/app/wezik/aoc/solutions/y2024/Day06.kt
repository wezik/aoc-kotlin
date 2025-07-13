package app.wezik.aoc.solutions.y2024

import app.wezik.aoc.domain.Solution
import app.wezik.aoc.domain.SolutionInput
import app.wezik.aoc.domain.SolutionResult
import app.wezik.aoc.domain.SolutionResult.Success

object Day06 : Solution() {

    // --- data structures and parsing ---

    private data class Guard(
        val pos: Vec,
        val dir: Vec,
    )

    private data class Vec(val x: Int, val y: Int) {
        operator fun plus(other: Vec) = Vec(x + other.x, y + other.y)

        companion object {
            val UP = Vec(0, -1)
            val DOWN = Vec(0, 1)
            val LEFT = Vec(-1, 0)
            val RIGHT = Vec(1, 0)
        }
    }

    private data class ParseOutput(
        val grid: Array<BooleanArray>,
        var guard: Guard,
    )

    private fun parse(input: SolutionInput): ParseOutput {
        val height = input.lines.size
        val width = input.lines.first().length
        val grid = Array(height) { BooleanArray(width) }
        var guard: Guard? = null

        for (y in 0 until height) {
            for (x in 0 until width) {
                when (input.lines[y][x]) {
                    '#' -> grid[y][x] = true
                    '^' -> guard = Guard(Vec(x, y), Vec.UP)
                }
            }
        }

        return ParseOutput(
            grid = grid,
            guard = guard!!,
        )
    }

    // --- solutions ---

    override fun part1(input: SolutionInput): SolutionResult {
        val (grid, guard) = parse(input)
        var (pos, dir) = guard
        val seen = mutableSetOf<Vec>()

        val width = grid[0].size
        val height = grid.size
        fun Vec.inBounds() = 0 <= x && x < width && 0 <= y && y < height
        while (pos.inBounds()) {
            seen += pos
            var newPos = pos + dir

            while (grid.getOrNull(newPos.y)?.getOrNull(newPos.x) == true) {
                dir = when (dir) {
                    Vec.UP -> Vec.RIGHT
                    Vec.RIGHT -> Vec.DOWN
                    Vec.DOWN -> Vec.LEFT
                    else -> Vec.UP
                }
                newPos = pos + dir
            }
            pos = newPos
        }

        val result = seen.size
        return Success(result)
    }

    override fun part2(input: SolutionInput): SolutionResult {
        val (grid, guard) = parse(input)
        var (pos, dir) = guard
        val cache = mutableListOf<Pair<Vec, Vec>>()

        // precompute the path with directions
        val width = grid[0].size
        val height = grid.size
        fun Vec.inBounds() = 0 <= x && x < width && 0 <= y && y < height
        while (pos.inBounds()) {
            cache += pos to dir
            var newPos = pos + dir

            while (grid.getOrNull(newPos.y)?.getOrNull(newPos.x) == true) {
                dir = when (dir) {
                    Vec.UP -> Vec.RIGHT
                    Vec.RIGHT -> Vec.DOWN
                    Vec.DOWN -> Vec.LEFT
                    else -> Vec.UP
                }
                newPos = pos // reset to previous position to cache in place rotation
            }

            pos = newPos
        }

        var count = 0
        // skip first position as it's the starting position
        for (i in 1 until cache.size) {
            val (candidate, _) = cache[i]
            val start = cache[i - 1]

            val seen = mutableSetOf<Pair<Vec, Vec>>()
            // add all previous positions to seen, no need to compute again
            seen += cache.subList(0, i - 1) 

            // skip if already visited, can't place the obstacle in the position guard has already been
            if (seen.find { (pos, _) -> pos == candidate } != null) continue 

            var (pos, dir) = start
            while (pos.inBounds()) {
                // loop detection
                if (pos to dir in seen) {
                    count++
                    break
                }

                seen += pos to dir

                var newPos = pos + dir

                if (grid.getOrNull(newPos.y)?.getOrNull(newPos.x) == true || newPos == candidate) {
                    dir = when (dir) {
                        Vec.UP -> Vec.RIGHT
                        Vec.RIGHT -> Vec.DOWN
                        Vec.DOWN -> Vec.LEFT
                        else -> Vec.UP
                    }
                    newPos = pos // reset to previous position to check in place rotation
                }
                pos = newPos
            }
        }
        return Success(count)
    }
}
