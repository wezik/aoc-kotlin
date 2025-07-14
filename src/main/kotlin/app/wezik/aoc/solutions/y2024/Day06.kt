package app.wezik.aoc.solutions.y2024

import app.wezik.aoc.domain.Solution
import app.wezik.aoc.domain.SolutionInput
import app.wezik.aoc.domain.SolutionResult
import app.wezik.aoc.domain.SolutionResult.Success

object Day06 : Solution() {

    // NOTE: this solution is hard to follow, mostly due to it being pretty slow unless heavily optimized
    // key optimizations done here are:
    // - (part2) using a pre-computed cache to skip alrady traversed paths
    // - (part2) starting calculations from the position just before the candidate for a box
    // - using very simplified data structures to avoid allocations overhead (it is significant here)
    // - (part2) using bit set to optimize lookups even further
    // - (part2) filtering zipped pairs of candidate to guard to only unique candidate positions
    // it all reduced part2 runtime (on my setup) from ~5 seconds to ~150ms
    // TODO: look into introducing reasonable jump maps, unless allocations would be a problem it should improve performance even more

    // --- data structures ---

    private class DirectionalBitSet(private val width: Int, private val height: Int) {
        private val size = width * height * Direction.values().size
        private val bits = LongArray(size / 64 + 1)

        private fun toBit(pos: Pair<Int, Int>, dir: Direction) =
            (pos.second * width + pos.first) * Direction.values().size + dir.ordinal

        fun clear(): Unit = bits.fill(0L)

        fun set(pos: Pair<Int, Int>, dir: Direction) {
            val bit = toBit(pos, dir)
            bits[bit / 64] = bits[bit / 64] or (1L shl (bit % 64))
        }

        fun isSet(pos: Pair<Int, Int>, dir: Direction): Boolean {
            val bit = toBit(pos, dir)
            return (bits[bit / 64] and (1L shl (bit % 64))) != 0L
        }
    }
    private data class DirectionalPos(val pos: Pair<Int, Int>, val dir: Direction)
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
        val (grid, guardPos) = parse(input)
        val height = grid.size
        val width = grid[0].size
        val guardOrigin = guardPos to Direction.UP

        var (cachePos, cacheDir) = guardOrigin

        val cache = mutableListOf<DirectionalPos>()

        while (cachePos.inBounds(width, height)) {
            cache += DirectionalPos(cachePos, cacheDir)
            var newPos = cachePos + cacheDir

            while (grid.getOrNull(newPos.second)?.getOrNull(newPos.first) == true) {
                cacheDir = cacheDir.rotate()
                newPos = cachePos // rotate in place
            }

            cachePos = newPos
        }

        var count = 0

        val seen = DirectionalBitSet(width, height)
        val zippedCache = cache.zipWithNext()
        val seenBoxes = mutableSetOf<Pair<Int, Int>>()
        // NOTE: filtered paths contains only unique candidates with their guard position pair
        val filteredPaths = mutableListOf<Pair<DirectionalPos, DirectionalPos>>()

        for ((guard, candidate) in zippedCache) {
            val (boxPos, _) = candidate
            if (seenBoxes.add(boxPos)) {
                filteredPaths += guard to candidate
            }
        }

        // zipped iteration for (candidate and guard) pairs
        filteredPaths.forEachIndexed { i, (guard, candidate) ->
            val cachedSeen = cache.subList(0, i)
            var (pos, dir) = guard
            val (candidate, _) = candidate

            seen.clear()
            cachedSeen.forEach { seen.set(it.pos, it.dir) }

            while (pos.inBounds(width, height)) {
                if (seen.isSet(pos, dir)) {
                    count++
                    break
                }

                seen.set(pos, dir)
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
