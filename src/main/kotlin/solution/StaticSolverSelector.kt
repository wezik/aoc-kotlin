package org.example.solution

import org.example.solution.solver.Solver
import org.example.solution.solver.days.*

class StaticSolverSelector {

    data class SolverSource(val solver: Solver, val path: String)

    private val solvers = listOf(
        SolverSource(Day1Solver(), "inputs/day1"),
        SolverSource(Day2Solver(), "inputs/day2"),
        SolverSource(Day3Solver(), "inputs/day3"),
        SolverSource(Day4Solver(), "inputs/day4"),
        SolverSource(Day5Solver(), "inputs/day5"),
    )

    fun select(day: Int): Solver {
        return if (day < solvers.size) {
            solvers[day].solver
        } else {
            throw IllegalArgumentException("Day $day not supported")
        }
    }

    fun selectAll(): List<SolverSource> {
        return solvers
    }

}
