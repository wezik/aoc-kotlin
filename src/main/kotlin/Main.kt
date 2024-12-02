package org.example

import org.example.solution.StaticSolverSelector
import org.example.solution.readFrom
import org.example.solution.solver.Solver
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

fun main(args: Array<String>) {

    val runAllValue = System.getenv("AOC_RUN_ALL") ?: "false"

    if (runAllValue == "true") {
        val solvers = StaticSolverSelector().selectAll()
        solvers.forEachIndexed { index, solver ->
            println("Running solver for day ${index + 1}")
            printSolution(solver.solver, solver.path)
        }
        // exit early
        return
    }

    if (args.size != 2) {
        println("Usage: kotlin Main.kt <day> <path>")
        // exit early
        return
    }
    val (day, path) = args

    val solver = StaticSolverSelector().select(day.toInt())

    printSolution(solver, path)

}

private fun printSolution(solver: Solver, path: String) {
    val solution = solver.solve(readFrom(path))

    val benchmarkValue = System.getenv("AOC_BENCHMARK") ?: "false"
    val isBenchmark = benchmarkValue == "true"

    val (p1Time, p2Time) = if (!isBenchmark) {
        Pair(solution.first.time.formatToMs(), solution.second.time.formatToMs())
    } else {
        runBenchmark(solver, path)
    }
    println("Part 1: \"${solution.first.value}\" generated in $p1Time")
    println("Part 2: \"${solution.second.value}\" generated in $p2Time")
}

private fun Duration.formatToMs() = this.toString(DurationUnit.MILLISECONDS, 3)

private fun runBenchmark(solver: Solver, path: String): Pair<String, String> {
    val p1Times = mutableListOf<Long>()
    val p2Times = mutableListOf<Long>()
    (1..1000).forEach {
        solver.solve(readFrom(path)).let {
            p1Times.add(it.first.time.inWholeNanoseconds)
            p2Times.add(it.second.time.inWholeNanoseconds)
        }
    }
    val p1Time = p1Times.average().toDuration(DurationUnit.NANOSECONDS).formatToMs() + " for ${p1Times.size} runs"
    val p2Time = p2Times.average().toDuration(DurationUnit.NANOSECONDS).formatToMs() + " for ${p2Times.size} runs"
    return p1Time to p2Time
}
