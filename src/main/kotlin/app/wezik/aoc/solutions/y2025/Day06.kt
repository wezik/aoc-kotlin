package app.wezik.aoc.solutions.y2025

import app.wezik.aoc.domain.Solution
import app.wezik.aoc.domain.SolutionInput
import app.wezik.aoc.domain.SolutionResult
import app.wezik.aoc.domain.SolutionResult.NotImplemented
import kotlin.math.abs
import kotlin.math.absoluteValue

object Day06 : Solution() {

    // This solution for part 1 now is very scuffed, and coded on the knee
    private enum class Operator {
        ADD,
        MULTIPLY,
    }

    private fun SolutionInput.parse(): Pair<List<List<Int>>, List<Operator>> {
        val numbersArray = mutableListOf(mutableListOf<Int>())
        val operators = mutableListOf<Operator>()
        var i: Int
        for (line in this.lines) {
            i = 0
            val entries = line.trim().split(" ").filter { it.isNotBlank() }
            entries.forEach { numbersArray.add(mutableListOf()) }
            for (entry in entries) {
                if (entry[0].isDigit()) {
                    numbersArray[i].add(entry.toInt())
                } else if (entry == "*") {
                    operators.add(Operator.MULTIPLY)
                } else if (entry == "+") {
                    operators.add(Operator.ADD)
                }

                i++
            }
        }
        return numbersArray to operators
    }

    override fun part1(input: SolutionInput): SolutionResult {
        val (numbersArray, operators) = input.parse()

        var sum = 0L
        for ((i, op) in operators.withIndex()) {
            if (op == Operator.ADD) {
                sum += numbersArray[i].sum()
            } else if (op == Operator.MULTIPLY) {
                var numberSum = 1L
                numbersArray[i].forEach { numberSum *= it }
                sum += numberSum
            }
        }
        return SolutionResult.Success(sum)
    }

    override fun part2(input: SolutionInput): SolutionResult {
        // Split inputs
        val lines = input.lines
        val maxLineLength = lines.maxOf { it.length }

        // Stores queue of numbers to their operator for given entry
        val inputQueue = mutableListOf<Pair<List<Int>, Char>>()
        // Stores temporary numbers before the entire entry is processed
        var numberQueue = mutableListOf<Int>()

        for (i in maxLineLength -1 downTo 0) {
            // Parse input in lines from right to left top to bottom
            val lineEntry = lines.mapNotNull { str -> str.getOrNull(i) }

            // Add numbers to temporary queue
            lineEntry.filter { it.isDigit() }.joinToString("").trim().toIntOrNull()?.let {
                numberQueue.add(it)
            }

            // Add operators and complete the entries as operators signal the end of one
            when {
                lineEntry.contains('*') -> {
                    inputQueue.add(numberQueue to '*')
                    numberQueue = mutableListOf()
                }
                lineEntry.contains('+') -> {
                    inputQueue.add(numberQueue to '+')
                    numberQueue = mutableListOf()
                }
            }
        }

        // Calculate the sum
        var sum = 0L
        for ((numbers, op) in inputQueue) {
            when (op) {
                '+' -> sum += numbers.sum()
                '*' -> {
                    var total = 1L
                    numbers.forEach { total *= it }
                    sum += total
                }
            }
        }

        return SolutionResult.Success(sum)
    }
}
