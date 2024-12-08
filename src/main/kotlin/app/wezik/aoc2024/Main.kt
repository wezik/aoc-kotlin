package app.wezik.aoc2024

import app.wezik.aoc2024.solution.StaticSolverSelector
import app.wezik.aoc2024.utils.readFrom
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

fun main(args: Array<String>) {
    val argsMap = args.toList().chunked(2).associate { it[0] to it[1] }
    val day = argsMap["-day"]
    val inputArg = argsMap["-input"]
    val benchmarkArg = argsMap["-benchmark"]

    val solvers = if (day == null) {
        println("Running all days in order")
        StaticSolverSelector().selectAll()
    } else {
        listOf(StaticSolverSelector().select(day))
    }

    solvers.forEach { solverSource ->
        val inputPath = inputArg ?: solverSource.path
        val input = readFrom(inputPath)
        if (benchmarkArg != null && benchmarkArg == "true") {
            println()
            println("[Day ${solverSource.ordinal + 1}] solver running...")
            val (p1Time, p2Time) = runBenchmark(solverSource)
            println(" Part 1 solution ${p1Time.formatToMs()} on average over $benchmarkRuns runs")
            println(" Part 2 solution ${p2Time.formatToMs()} on average over $benchmarkRuns runs")
            return@forEach
        }
        println()
        println("[Day ${solverSource.ordinal + 1}] solver running...")
        val solution = solverSource.solver.solve(input)
        println(" Part 1 solution in ${solution.part1.time.formatToSeconds()}: \"${solution.part1.result}\"")
        println(" Part 2 solution in ${solution.part2.time.formatToSeconds()}: \"${solution.part2.result}\"")
    }
}

val benchmarkRuns = 100

private fun Duration.formatToSeconds() = this.toString(DurationUnit.SECONDS, 3)
private fun Duration.formatToMs() = this.toString(DurationUnit.MILLISECONDS, 3)

private fun runBenchmark(source: StaticSolverSelector.SolverSource): Pair<Duration, Duration> {
    val p1Times = mutableListOf<Long>()
    val p2Times = mutableListOf<Long>()
    (0 until benchmarkRuns).forEach {
        source.solver.solve(readFrom(source.path)).let {
            p1Times.add(it.part1.time.inWholeNanoseconds)
            p2Times.add(it.part2.time.inWholeNanoseconds)
        }
    }
    val p1Time = p1Times.average().toDuration(DurationUnit.NANOSECONDS)
    val p2Time = p2Times.average().toDuration(DurationUnit.NANOSECONDS)
    return p1Time to p2Time
}
