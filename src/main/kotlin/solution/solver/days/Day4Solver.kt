package org.example.solution.solver.days

import org.example.solution.solver.Result
import org.example.solution.solver.Solver
import org.example.solution.time
import org.example.solution.toNanoDuration

class Day4Solver : Solver {

    override fun solve(input: List<String>): Pair<Result, Result> {
        var part1Sum = 0
        val part1Time = time {
            part1Sum = runSolution(input)
        }.toNanoDuration()
        val part1Result = Result(part1Sum.toString(), part1Time)

        var part2Sum = 0
        val part2Time = time {
            part2Sum = runSolution(input)
        }.toNanoDuration()
        val part2Result = Result(part2Sum.toString(), part2Time)

        return part1Result to part2Result
    }

    private fun runSolution(input: List<String>): Int {
        var sum = 0
        for (y in 0 until input.size) {
            for (x in 0 until input[y].length) {
                if (input[y][x] == 'X') {
                    sum += findXmas(x, y, input)
                }
            }
        }
        return sum
    }

    // Pass the location of 'X' to find all "XMAS's"
    private fun findXmas(x: Int, y: Int, input: List<String>): Int {
        var sum = 0

        // Left
        if (!input.isOutOfBounds(x = x - 3)) {
            sum += input.checkRange(x - 1 downTo x - 3, y..y)
        }
        // Up
        if (!input.isOutOfBounds(y = y - 3)) {
            sum += input.checkRange(x..x, y - 1 downTo y - 3)
        }
        // Right
        if (!input.isOutOfBounds(x = x + 3)) {
            sum += input.checkRange(x + 1..x + 3, y..y)
        }
        // Down
        if (!input.isOutOfBounds(y = y + 3)) {
            sum += input.checkRange(x..x, y + 1..y + 3)
        }
        // Left-Up
        if (!input.isOutOfBounds(x = x - 3, y = y - 3)) {
            sum += input.checkRange(x - 1 downTo x - 3, y - 1 downTo y - 3)
        }
        // Right-Up
        if (!input.isOutOfBounds(x = x + 3, y = y - 3)) {
            sum += input.checkRange(x + 1..x + 3, y - 1 downTo y - 3)
        }
        // Left-Down
        if (!input.isOutOfBounds(x = x - 3, y = y + 3)) {
            sum += input.checkRange(x - 1 downTo x - 3, y + 1..y + 3)
        }
        // Right-Down
        if (!input.isOutOfBounds(x = x + 3, y = y + 3)) {
            sum += input.checkRange(x + 1..x + 3, y + 1..y + 3)
        }

        return sum
    }

    companion object {
        private const val XMAS = "XMAS"
    }

    // Don't pass possible out of bounds values and ranges other than 3!!!
    private fun List<String>.checkRange(xRange: IntProgression, yRange: IntProgression): Int {
        val xDefault = xRange.first
        val yDefault = yRange.first

        val xRangeI = xRange.iterator()
        val yRangeI = yRange.iterator()

        var xmasIndex = 1

        while (xmasIndex < XMAS.length) {
            var xIndex = if (xRangeI.hasNext()) xRangeI.next() else xDefault
            var yIndex = if (yRangeI.hasNext()) yRangeI.next() else yDefault
            if (this[yIndex][xIndex] != XMAS[xmasIndex]) return 0
            ++xmasIndex
        }
        return 1
    }

    private fun List<String>.isOutOfBounds(x: Int? = null, y: Int? = null): Boolean {
        var expression = false
        if (x != null) {
            // Checking on length of the first line, they all should be the same length
            expression = expression || (x < 0 || x >= this[0].length)
        }
        if (y != null) {
            expression = expression || (y < 0 || y >= this.size)
        }
        return expression
    }

}
