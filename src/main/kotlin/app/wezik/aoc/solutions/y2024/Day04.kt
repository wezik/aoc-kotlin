package app.wezik.aoc.solutions.y2024

import app.wezik.aoc.domain.Solution
import app.wezik.aoc.domain.SolutionInput
import app.wezik.aoc.domain.SolutionResult
import app.wezik.aoc.domain.SolutionResult.Success

object Day04 : Solution() {

    private enum class Direction(val x: Int, val y: Int) {
        NORTH(0, -1),
        NORTH_WEST(-1, -1),
        NORTH_EAST(1, -1),
        SOUTH(0, 1),
        SOUTH_WEST(-1, 1),
        SOUTH_EAST(1, 1),
        WEST(-1, 0),
        EAST(1, 0);

        operator fun component1(): Int = x
        operator fun component2(): Int = y
    }
 
    override fun part1(input: SolutionInput): SolutionResult {
        var count = 0
        val pattern = "XMAS"
        val puzzle = input.lines

        for (y in 0 until puzzle.size) {
            for (x in 0 until puzzle[y].length) {
                if (puzzle[y][x] != pattern[0]) continue

                outer@ for ((dx, dy) in Direction.values()) {
                    val leftover = pattern.length - 1

                    // out of bounds check
                    if (y + dy * leftover < 0 || y + dy * leftover >= puzzle.size) continue
                    if (x + dx * leftover < 0 || x + dx * leftover >= puzzle[0].length) continue

                    for (offset in 1 until pattern.length) {
                        val cx = x + dx * offset
                        val cy = y + dy * offset
                        if (puzzle[cy][cx] != pattern[offset]) continue@outer
                    }
                    count++
                }
            }
        }

        return Success(count)
    }

    override fun part2(input: SolutionInput): SolutionResult {
        var count = 0
        // pattern needs to be of length 3 to work
        val pattern = "MAS"
        val puzzle = input.lines
        val combos = listOf(
            Direction.NORTH_WEST to Direction.SOUTH_EAST,
            Direction.NORTH_EAST to Direction.SOUTH_WEST,
            Direction.SOUTH_WEST to Direction.NORTH_EAST,
            Direction.SOUTH_EAST to Direction.NORTH_WEST
        )

        // trim index ranges by 1 since we don't care about the edges
        for (y in 1 until puzzle.size - 1) {
            for (x in 1 until puzzle[y].length - 1) {
                if (puzzle[y][x] != pattern[1]) continue

                var comboCount = 0
                for ((start, end) in combos) {
                    val sx = x + start.x
                    val sy = y + start.y
                    val ex = x + end.x
                    val ey = y + end.y

                    if (puzzle[sy][sx] != pattern[0]) continue
                    if (puzzle[ey][ex] != pattern[2]) continue

                    comboCount++
                    // 2 combos make a cross
                    if (comboCount == 2) {
                        count++
                        break
                    }
                }
            }
        }

        return Success(count)
    }
}
