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
//            for (y in 0..newList.size - 1) {
//                println(newList[y].joinToString("") { "[$it]" })
//            }
        }.toNanoDuration()
        val part1Result = Result(part1Sum.toString(), part1Time)

        var part2Sum = 0
        val part2Time = time {
            part2Sum = runSolution(input)
        }.toNanoDuration()
        val part2Result = Result(part2Sum.toString(), part2Time)

        return part1Result to part2Result
    }

    companion object {
        private var newList: MutableList<MutableList<String>> = mutableListOf()
    }

    private fun runSolution(input: List<String>): Int {
        input.forEachIndexed { indexY, it ->
            newList.add(mutableListOf())
            it.forEach { it ->
                newList[indexY].add(". ")
            }
        }

        var sum = 0
        for (y in 0..input.size - 1) {
            for (x in 0..input[y].length - 1) {
                val c = input[y][x]
                if (c == 'X') {
                    newList[y][x] = newList[y][x].replace(".", "X")
                    // Right
                    if (x + 3 < input[y].length) {
                        sum += input.findXmas(x + 1..x + 3, y..y)
                    }
                    // Left
                    if (x - 3 >= 0) {
                        sum += input.findXmas(x - 1..x - 3, y..y)
                    }
                    // Up
                    if (y - 3 >= 0) {
                        sum += input.findXmas(x..x, y - 1..y - 3)
                    }
                    // Down
                    if (y + 3 < input.size) {
                        sum += input.findXmas(x..x, y + 1..y + 3)
                    }
                    // Up-Left
                    if (x - 3 >= 0 && y - 3 >= 0) {
                        sum += input.findXmas((x - 1..x - 3), y - 1..y - 3)
                    }
                    // Up-Right
                    if (x + 3 < input[y].length && y - 3 >= 0) {
                        sum += input.findXmas(x + 1..x + 3, y - 1..y - 3)
                    }
                    // Down-Left
                    if (x - 3 >= 0 && y + 3 < input.size) {
                        sum += input.findXmas(x - 1..x - 3, y + 1..y + 3)
                    }
                    // Down-Right
                    if (x + 3 < input[y].length && y + 3 < input.size) {
                        sum += input.findXmas(x + 1..x + 3, y + 1..y + 3)
                    }
                }
            }
        }
        return sum
    }

    // Don't pass possible out of bounds values
    private fun List<String>.findXmas(xRange: IntRange, yRange: IntRange): Int {
        var xmasIndex = 1 // No need to check for 'X' at index 0
        var xmasString = "XMAS"

        val xDefault = xRange.first
        val yDefault = yRange.first

        val xRangeList = xRange.toList()
        val yRangeList = yRange.toList()

        println("xRangeList: $xRangeList")
        println("yRangeList: $yRangeList")

        var xRangeI = 0
        var yRangeI = 0

        while (xmasIndex < xmasString.length) {
            var x = xDefault
            if (xRangeI < xRangeList.size) {
                x = xRangeList[xRangeI++]
            }
            var y = yDefault
            if (yRangeI < yRangeList.size) {
                y = yRangeList[yRangeI++]
            }
            val c = this[y][x]
            newList[y][x] = newList[y][x].replace(" ", "*")
            if (c != xmasString[xmasIndex]) {
                return 0
            }
            newList[y][x] = newList[y][x].replace('.', xmasString[xmasIndex])
            ++xmasIndex
        }
        return 1
    }


}
