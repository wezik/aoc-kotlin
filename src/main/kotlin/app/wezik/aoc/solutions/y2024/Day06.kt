package app.wezik.aoc.solutions.y2024

import app.wezik.aoc.domain.Solution
import app.wezik.aoc.domain.SolutionInput
import app.wezik.aoc.domain.SolutionResult
import app.wezik.aoc.domain.SolutionResult.Success

object Day06 : Solution() {

    // NOTE: this solution is hard to follow, mostly due to it being pretty slow unless heavily optimized
    // key optimizations implemented (in order):
    // - (part2) using a pre-computed cache to skip alrady traversed paths
    // - (part2) starting calculations from the position just before the candidate for a box
    // - using very simplified data structures to avoid allocations overhead (it is significant here)
    // - (part2) using directional bitset to optimize lookups even further
    // - (part2) filtering zipped pairs of candidate to guard to only unique candidate positions
    // - (part2) removing cache copying for previously seen paths (copy was more expensive than the repeated lookups)
    // - (part2) bitset for grid lookup
    // it all reduced part2 runtime (on my setup) from ~5s to ~100ms
    // TODO: look into introducing reasonable jump maps, unless allocations would be a problem it should improve performance even more

    // --- data structures ---

    private class GridBitSet(val width: Int, val height: Int) {
        private val size = width * height
        private val bits = LongArray((size + 63) / 64)

        fun set(pos: Pair<Int, Int>) {
            val bit = toBit(pos)
            bits[bit / 64] = bits[bit / 64] or (1L shl (bit % 64))
        }

        fun get(pos: Pair<Int, Int>): Boolean {
            val bit = toBit(pos)
            return (bits[bit / 64] and (1L shl (bit % 64))) != 0L
        }

        private fun toBit(pos: Pair<Int, Int>) = pos.second * width + pos.first
        fun isInBounds(pos: Pair<Int, Int>): Boolean = pos.first in 0 until width && pos.second in 0 until height
        fun isObstacle(pos: Pair<Int, Int>): Boolean = isInBounds(pos) && get(pos)
    }
    private class DirectionalGridBitSet(private val width: Int, private val height: Int) {
        private val size = width * height * Direction.values().size
        private val bits = LongArray(size / 64 + 1)

        private fun toBit(pos: Pair<Int, Int>, dir: Direction) =
            (pos.second * width + pos.first) * Direction.values().size + dir.ordinal

        fun clear(): Unit = bits.fill(0L)

        fun set(pos: Pair<Int, Int>, dir: Direction) {
            val bit = toBit(pos, dir)
            bits[bit / 64] = bits[bit / 64] or (1L shl (bit % 64))
        }

        fun get(pos: Pair<Int, Int>, dir: Direction): Boolean {
            val bit = toBit(pos, dir)
            return (bits[bit / 64] and (1L shl (bit % 64))) != 0L
        }
    }
    private data class DirectionalPos(val pos: Pair<Int, Int>, val dir: Direction)
    private enum class Direction { UP, RIGHT, DOWN, LEFT }
    private data class ParseOutput(
        val grid: GridBitSet,
        var guard: Pair<Int, Int>,
    )

    // --- parsing ---

    private fun parse(input: SolutionInput): ParseOutput {
        val height = input.lines.size
        val width = input.lines.first().length
        val grid = GridBitSet(width, height)
        var guard: Pair<Int, Int>? = null

        for (y in 0 until height) {
            for (x in 0 until width) {
                when (input.lines[y][x]) {
                    '#' -> grid.set(x to y)
                    '^' -> guard = x to y
                }
            }
        }

        return ParseOutput(
            grid = grid,
            guard = guard!!,
        )
    }

    // --- util functions ---

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
        val (grid, guard) = parse(input)

        var (pos, dir) = guard to Direction.UP
        val seen = GridBitSet(grid.width, grid.height)

        while (grid.isInBounds(pos)) {
            seen.set(pos)
            var newPos = pos + dir

            while (grid.isInBounds(newPos) && grid.get(newPos)) {
                dir = dir.rotate()
                newPos = pos + dir
            }

            pos = newPos
        }
        var count = 0
        for (y in 0 until grid.height) {
            for (x in 0 until grid.width) {
                if (seen.get(x to y)) {
                    count++
                }
            }
        }
        return Success(count)
    }

    override fun part2(input: SolutionInput): SolutionResult {
        val (grid, pos) = parse(input)

        val guardOrigin = pos to Direction.UP
        var (cachePos, cacheDir) = guardOrigin

        val cache = mutableListOf<DirectionalPos>()

        while (grid.isInBounds(cachePos)) {
            cache += DirectionalPos(cachePos, cacheDir)
            var newPos = cachePos + cacheDir

            while (grid.isObstacle(newPos)) {
                cacheDir = cacheDir.rotate()
                newPos = cachePos // rotate in place
            }

            cachePos = newPos
        }


        // NOTE: we don't care about candidate direction so we can cut it out of the zip
        val guardWithCandidate = cache.zipWithNext().map { (a, b) -> a to b.pos }

        // NOTE: filter guard with candidate zip to only unique candidates with their guard position pair
        val seenBoxes = mutableSetOf<Pair<Int, Int>>()
        val filteredPaths = mutableListOf<Pair<DirectionalPos, Pair<Int, Int>>>()
        for ((guard, candidate) in guardWithCandidate) {
            if (seenBoxes.add(candidate)) {
                filteredPaths += guard to candidate
            }
        }

        val seen = DirectionalGridBitSet(grid.width, grid.height)
        var count = 0

        // zipped iteration for (candidate and guard) pairs
        filteredPaths.forEachIndexed { i, (guard, candidate) ->
            var (pos, dir) = guard
            seen.clear()

            while (grid.isInBounds(pos)) {
                if (seen.get(pos, dir)) {
                    count++
                    break
                }

                seen.set(pos, dir)
                var newPos = pos + dir

                if (grid.isObstacle(newPos) || newPos == candidate) {
                    dir = dir.rotate()
                    newPos = pos // reset to previous position to check in place rotation
                }
                pos = newPos
            }
        }

        return Success(count)
    }
}
