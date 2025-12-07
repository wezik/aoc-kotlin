package app.wezik.aoc.solutions.y2025

import app.wezik.aoc.domain.Solution
import app.wezik.aoc.domain.SolutionInput
import app.wezik.aoc.domain.SolutionResult
import app.wezik.aoc.domain.SolutionResult.NotImplemented
import kotlin.math.abs
import kotlin.math.absoluteValue

object Day06 : Solution() {

    // This solution for now is very scuffed, and coded on a knee
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
        return SolutionResult.Success(0)
    }
}
