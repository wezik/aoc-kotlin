package app.wezik.aoc2024.solution.days

import app.wezik.aoc2024.solution.Solver

class Day4Solver : Solver {

    override fun part1(input: List<String>): String {
        var sum = 0
        for (y in 0 until input.size) {
            for (x in 0 until input[y].length) {
                if (input[y][x] == 'X') {
                    sum += findXmas(x, y, input)
                }
            }
        }
        return sum.toString()
    }

    override fun part2(input: List<String>): String {
        var sum = 0
        // We can start from index 1 since 'A' has to be in the middle of the cross
        // Same rule applies to maximum value
        for (y in 1 until input.size - 1) {
            // Same here, as above
            for (x in 1 until input[y].length - 1) {
                if (input[y][x] == 'A') {
                    sum += findCrossMas(x, y, input)
                }
            }
        }
        return sum.toString()

    }

    // Pass the location of 'X' to find all "XMAS's"
    private fun findXmas(x: Int, y: Int, input: List<String>): Int {
        var sum = 0

        // Left
        if (!input.isOutOfBounds(x = x - 3)) {
            sum += input.checkRangeForXmas(x - 1 downTo x - 3, y..y)
        }
        // Up
        if (!input.isOutOfBounds(y = y - 3)) {
            sum += input.checkRangeForXmas(x..x, y - 1 downTo y - 3)
        }
        // Right
        if (!input.isOutOfBounds(x = x + 3)) {
            sum += input.checkRangeForXmas(x + 1..x + 3, y..y)
        }
        // Down
        if (!input.isOutOfBounds(y = y + 3)) {
            sum += input.checkRangeForXmas(x..x, y + 1..y + 3)
        }
        // Left-Up
        if (!input.isOutOfBounds(x = x - 3, y = y - 3)) {
            sum += input.checkRangeForXmas(x - 1 downTo x - 3, y - 1 downTo y - 3)
        }
        // Right-Up
        if (!input.isOutOfBounds(x = x + 3, y = y - 3)) {
            sum += input.checkRangeForXmas(x + 1..x + 3, y - 1 downTo y - 3)
        }
        // Left-Down
        if (!input.isOutOfBounds(x = x - 3, y = y + 3)) {
            sum += input.checkRangeForXmas(x - 1 downTo x - 3, y + 1..y + 3)
        }
        // Right-Down
        if (!input.isOutOfBounds(x = x + 3, y = y + 3)) {
            sum += input.checkRangeForXmas(x + 1..x + 3, y + 1..y + 3)
        }

        return sum
    }

    companion object {
        private const val XMAS = "XMAS"
    }

    // Don't pass possible out of bounds values and ranges other than 3!!!
    private fun List<String>.checkRangeForXmas(xRange: IntProgression, yRange: IntProgression): Int {
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

    private fun findCrossMas(x: Int, y: Int, input: List<String>): Int {
        // Dont need to check out of bounds since the values can't reach that point
        val ltrCase1 = input[y - 1][x - 1] == 'S' && input[y + 1][x + 1] == 'M'
        val ltrCase2 = input[y - 1][x - 1] == 'M' && input[y + 1][x + 1] == 'S'
        val leftToRightCross = ltrCase1 || ltrCase2

        // Early return if not present
        if (!leftToRightCross) return 0

        val rtlCase1 = input[y - 1][x + 1] == 'S' && input[y + 1][x - 1] == 'M'
        val rtlCase2 = input[y - 1][x + 1] == 'M' && input[y + 1][x - 1] == 'S'
        val rightToLeftCross = rtlCase1 || rtlCase2

        // Only rightToLeftCross since leftToRightCross is already checked and true at this point
        return if (rightToLeftCross) 1 else 0
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
