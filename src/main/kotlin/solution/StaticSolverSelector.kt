package org.example.solution

import org.example.solution.solver.Solver
import org.example.solution.solver.days.Day1Solver

class StaticSolverSelector {

    data class SolverSource(val solver: Solver, val path: String)

    private val solvers = listOf(
        SolverSource(Day1Solver(), "inputs/day1")
    )

    fun select(day: String): Solver {
        return when (day) {
            "1" -> Day1Solver()
            else -> throw IllegalArgumentException("Day $day not supported")
        }
    }

    fun selectAll(): List<SolverSource> {
        return solvers
    }

}
