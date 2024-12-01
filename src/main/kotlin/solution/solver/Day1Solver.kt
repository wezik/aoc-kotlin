package org.example.solution.solver

import org.example.solution.InputSolver
import kotlin.math.abs

class Day1Solver : InputSolver {

    override fun solve(input: List<String>): String {
        // Early return on empty input
        if (input.isEmpty()) return "0"

        val pairs = input.toPairs(input)

        val (a, b) = pairs.split()

        return a.sorted().zip(b.sorted()).map {
            abs(it.first - it.second)
        }.sum().toString()
    }

    private fun List<String>.toPairs(input: List<String>): List<Pair<Int, Int>> {
        return input.map { line ->
            val parts = line.split(' ').filter { it.isNotBlank() }
            Pair(parts[0].toInt(), parts[1].toInt())
        }
    }

    private fun List<Pair<Int, Int>>.split(): Pair<List<Int>, List<Int>> {
        return Pair(this.map { it.first }, this.map { it.second })
    }
}
