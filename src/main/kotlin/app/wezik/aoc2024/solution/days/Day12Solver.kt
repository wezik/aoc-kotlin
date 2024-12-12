package app.wezik.aoc2024.solution.days

import app.wezik.aoc2024.solution.Solver

class Day12Solver : Solver {

    private data class Context(val input: List<String>) {
        val rows = input.size
        val cols = input.first().length
        val visited = Array(rows) { BooleanArray(cols) }
        var totalPrice = 0L
    }

    private data class Region(val size: Int, val edges: Int, val sides: Int) {
        fun getPrice(part2: Boolean = false): Long {
            return if (part2) (size * sides).toLong() else (size * edges).toLong()
        }
    }

    private fun Context.isValid(x: Int, y: Int) = y in 0 until rows && x in 0 until cols

    private fun Context.getPrice(part2: Boolean = false): Long {
        for (y in 0 until rows) {
            for (x in 0 until cols) {
                if (visited[y][x]) continue
                val char = input[y][x]
                val region = floodFill(x, y, char, part2)
                totalPrice += region.getPrice(part2)
            }
        }
        return totalPrice
    }

    private fun Context.floodFill(x: Int, y: Int, char: Char, part2: Boolean = false): Region {
        val directions = listOf(
            Pair(-1, 0), // Up
            Pair(1, 0),  // Down
            Pair(0, -1), // Left
            Pair(0, 1)   // Right
        )

        var size = 0
        var edges = 0
        var sides = 0
        val stack = mutableListOf(Pair(x, y))
        visited[y][x] = true

        // Use a set to track all cells belonging to this region
        val regionCells = mutableSetOf(Pair(x, y))

        while (stack.isNotEmpty()) {
            val (currentX, currentY) = stack.removeLast()
            size++

            for ((directionX, directionY) in directions) {
                val nextX = currentX + directionX
                val nextY = currentY + directionY

                // If the next cell is out of bounds or a different character, it's an edge
                if (!isValid(nextX, nextY) || input[nextY][nextX] != char) {
                    edges++
                    continue
                }

                // If the next cell is already visited, skip it
                if (visited[nextY][nextX]) continue

                // Mark as visited and add to stack
                visited[nextY][nextX] = true
                stack.add(Pair(nextX, nextY))
                regionCells.add(Pair(nextX, nextY))
            }
        }

        // Calculate sides if part2 is enabled
        if (part2) {
            sides = calculateSides(regionCells)
        }

        return Region(size, edges, sides)
    }

    private enum class Direction(val x: Int, val y: Int) {
        UP(0, -1), DOWN(0, 1), LEFT(-1, 0), RIGHT(1, 0);

        fun getLeft() = when (this) {
            UP -> LEFT
            DOWN -> RIGHT
            LEFT -> DOWN
            RIGHT -> UP
        }

        fun getRight() = when (this) {
            UP -> RIGHT
            DOWN -> LEFT
            LEFT -> UP
            RIGHT -> DOWN
        }
    }

    private fun calculateSides(regionCells: Set<Pair<Int, Int>>): Int {
        val cornerCandidates = regionCells.flatMap { (x, y) ->
            setOf(
                x - 0.5 to y - 0.5,
                x + 0.5 to y - 0.5,
                x + 0.5 to y + 0.5,
                x - 0.5 to y + 0.5,
            )
        }.toSet()

        var corners = 0
        for ((x, y) in cornerCandidates) {
            val adjacentCells = listOf(
                x - 0.5 to y - 0.5,
                x + 0.5 to y - 0.5,
                x + 0.5 to y + 0.5,
                x - 0.5 to y + 0.5,
            ).map { (checkX, checkY) -> checkX.toInt() to checkY.toInt() in regionCells }
            when (adjacentCells.filter { it }.count()) {
                1 -> corners++
                2 -> {
                    if (adjacentCells[0] && adjacentCells[2]) {
                        corners += 2
                    } else if (adjacentCells[1] && adjacentCells[3]) {
                        corners += 2
                    }
                }
                3 -> corners++
            }
        }
        return corners.toInt()
    }

    override fun part1(input: List<String>) = Context(input).getPrice().toString()

    override fun part2(input: List<String>) = Context(input).getPrice(part2 = true).toString()
}
