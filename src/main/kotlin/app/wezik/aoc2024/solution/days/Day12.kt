package app.wezik.aoc2024.solution.days

import app.wezik.aoc2024.solution.Solver

class Day12 : Solver {

    private data class InputWrapper(val grid: List<String>) {
        val rows = grid.size
        val cols = grid.first().length
        val visited = Array(rows) { BooleanArray(cols) }
    }

    private fun InputWrapper.isValid(x: Int, y: Int) = y in 0 until rows && x in 0 until cols

    private companion object {
        val DIRECTIONS = listOf(
            Pair(0, -1), // Up
            Pair(0, 1),  // Down
            Pair(-1, 0), // Left
            Pair(1, 0),  // Right
        )
        val CORNER_OFFSETS = listOf(
            Pair(-0.5, -0.5), // Up Left
            Pair(0.5, -0.5),  // Up Right
            Pair(0.5, 0.5),   // Down Right
            Pair(-0.5, 0.5),  // Down Left
        )
    }

    private fun InputWrapper.getRegionPrice(x: Int, y: Int, discount: Boolean): Long {
        var edges = 0
        var size = 0
        val stack = mutableListOf(x to y)
        val regionCells = mutableSetOf(x to y) // Keep track of all cells for corner calculation
        val cellId = grid[y][x]
        visited[y][x] = true

        while (stack.isNotEmpty()) {
            val (x, y) = stack.removeLast()
            size++

            for ((dirX, dirY) in DIRECTIONS) {
                val nextX = x + dirX
                val nextY = y + dirY

                // If the next cell is out of bounds or a different character, it's an edge
                if (!isValid(nextX, nextY) || grid[nextY][nextX] != cellId) {
                    edges++
                    continue
                }

                // If the next cell is already visited, skip it
                if (visited[nextY][nextX]) continue

                // Mark as visited and add to stack
                visited[nextY][nextX] = true
                stack.add(nextX to nextY)
                if (discount) regionCells.add(nextX to nextY)
            }
        }

        // Calculate amount of corners if discount is enabled
        return if (discount) {
            size.toLong() * countCorners(regionCells)
        } else {
            size.toLong() * edges
        }
    }

    fun countCorners(regionCells: Set<Pair<Int, Int>>): Int {
        // Create corners for each cell in the region
        val cornerCandidates = regionCells.flatMap { (x, y) ->
            CORNER_OFFSETS.map { (xOffset, yOffset) -> x + xOffset to y + yOffset }
        }.toSet()

        var cornersTotal = 0
        for ((x, y) in cornerCandidates) {
            // Check adjacent cells for all corners
            val adjacentCells = CORNER_OFFSETS
                .map { (xOffset, yOffset) -> x + xOffset to y + yOffset }
                .map { (xCell, yCell) -> xCell.toInt() to yCell.toInt() in regionCells }

            // 1 & 3 cells are corners, 2 cells is an edge case separating two inner regions sharing a wall (requirement)
            val cellCount = adjacentCells.filter { it == true }.count()
            when {
                cellCount == 1 -> cornersTotal++
                cellCount == 2 && adjacentCells[0] && adjacentCells[2] -> cornersTotal += 2
                cellCount == 2 && adjacentCells[1] && adjacentCells[3] -> cornersTotal += 2
                cellCount == 3 -> cornersTotal++
            }
        }
        return cornersTotal
    }

    private fun List<String>.getPrice(discount: Boolean = false): Long {
        var totalPrice = 0L
        val input = InputWrapper(this)
        for (y in 0 until input.rows) {
            for (x in 0 until input.cols) {
                if (input.visited[y][x]) continue // Skip if already visited
                totalPrice += input.getRegionPrice(x, y, discount)
            }
        }
        return totalPrice
    }

    override fun part1(input: List<String>) = input.getPrice().toString()
    override fun part2(input: List<String>) = input.getPrice(discount = true).toString()

}
