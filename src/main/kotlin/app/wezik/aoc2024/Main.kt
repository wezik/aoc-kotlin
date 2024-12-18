package app.wezik.aoc2024

import app.wezik.aoc2024.solution.StaticSolverSelector
import app.wezik.aoc2024.solution.StaticSolverSelector.SolverSource
import app.wezik.aoc2024.utils.readFrom
import app.wezik.aoc2024.utils.time
import app.wezik.aoc2024.utils.toNanoDuration
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

// some are just slow unfortunately
private val hardcodedRunLimits = mapOf(
    SolverSource.DAY06 to 10,
    SolverSource.DAY11 to 500,
    SolverSource.DAY14 to 100,
    SolverSource.DAY16 to 100,
)

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
        if (benchmarkArg != null) {
            var benchmarkRuns = benchmarkArg.toInt()
            // capping runs for some days to avoid long running benchmarks
            if (hardcodedRunLimits.containsKey(solverSource)) {
                benchmarkRuns = minOf(benchmarkRuns, hardcodedRunLimits[solverSource]!!)
            }
            val day = if (solverSource.ordinal < 9) "0${solverSource.ordinal + 1}" else solverSource.ordinal + 1
            val totalTime = time {
                println("[Day $day]:")
                val (p1Time, p2Time) = runBenchmark(solverSource, input, benchmarkRuns)
                println("  Part 1 average: ${p1Time.formatToMs()}")
                println("  Part 2 average: ${p2Time.formatToMs()}")
            }.toNanoDuration()
            println("  Total runs: $benchmarkRuns")
            println("  Total time: $totalTime (${totalTime.formatToSeconds()})")
            return@forEach
        }
        println()
        val solution = solverSource.solver.solve(input)
        println(" Part 1 solution in ${solution.part1.time.formatToSeconds()}: \"${solution.part1.result}\"")
        println(" Part 2 solution in ${solution.part2.time.formatToSeconds()}: \"${solution.part2.result}\"")
    }
}

private fun Duration.formatToSeconds() = this.toString(DurationUnit.SECONDS, 3)
private fun Duration.formatToMs() = this.toString(DurationUnit.MILLISECONDS, 3)

private fun runBenchmark(
    source: StaticSolverSelector.SolverSource,
    input: List<String>,
    benchmarkRuns: Int,
): Pair<Duration, Duration> {
    val p1Times = mutableListOf<Long>()
    val p2Times = mutableListOf<Long>()

    // warmup
    repeat(5) { source.solver.solve(input) }

    (0 until benchmarkRuns).forEach {
        source.solver.solve(input).let {
            p1Times.add(it.part1.time.inWholeNanoseconds)
            p2Times.add(it.part2.time.inWholeNanoseconds)
        }
    }
    val p1Time = p1Times.average().toDuration(DurationUnit.NANOSECONDS)
    val p2Time = p2Times.average().toDuration(DurationUnit.NANOSECONDS)
    return p1Time to p2Time
}
