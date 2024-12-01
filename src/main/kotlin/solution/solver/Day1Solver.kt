package org.example.solution.solver

import org.example.solution.InputSolver
import org.example.solution.reader.Day1Reader

class Day1Solver : InputSolver {
    val reader = Day1Reader()

    override fun solve(path: String): String {
        val input = reader.read(path)
        return input
    }
}
