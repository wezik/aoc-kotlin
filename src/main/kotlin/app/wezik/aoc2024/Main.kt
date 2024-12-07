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
            val (p1Time, p2Time) = runBenchmark(solverSource)
            val solution = solverSource.solver.solve(input)
            println("===========================================")
            println("Part 1: \"${solution.part1.result}\" generated in $p1Time")
            println("Part 2: \"${solution.part2.result}\" generated in $p2Time")
            println("===========================================")
            return@forEach
        }
        val solution = solverSource.solver.solve(input)
        println("===========================================")
        println("Part 1: \"${solution.part1.result}\" generated in ${solution.part1.time.formatToMs()}")
        println("Part 2: \"${solution.part2.result}\" generated in ${solution.part2.time.formatToMs()}")
        println("===========================================")
    }
}

private fun Duration.formatToMs() = this.toString(DurationUnit.MILLISECONDS, 3)

private fun runBenchmark(source: StaticSolverSelector.SolverSource): Pair<String, String> {
    val p1Times = mutableListOf<Long>()
    val p2Times = mutableListOf<Long>()
    (1..1000).forEach {
        source.solver.solve(readFrom(source.path)).let {
            p1Times.add(it.part1.time.inWholeNanoseconds)
            p2Times.add(it.part2.time.inWholeNanoseconds)
        }
    }
    val p1Time =
        p1Times.average().toDuration(DurationUnit.NANOSECONDS).formatToMs() + " average for ${p1Times.size} runs"
    val p2Time =
        p2Times.average().toDuration(DurationUnit.NANOSECONDS).formatToMs() + " average for ${p2Times.size} runs"
    return p1Time to p2Time
}
