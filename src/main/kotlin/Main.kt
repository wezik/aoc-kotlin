package org.example

import org.example.solution.solver.InputSolver
import org.example.solution.readFrom
import org.example.solution.solver.Day1Solver
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

fun main(args: Array<String>) {

    if (args.size != 2) {
        println("Usage: kotlin Main.kt <day> <path>")
        return
    }
    val (day, path) = args

    val solver = when (day) {
        "1" -> Day1Solver()
        else -> throw IllegalArgumentException("Day $day not supported")
    }

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

private fun runBenchmark(solver: InputSolver, path: String): Pair<String, String> {
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
