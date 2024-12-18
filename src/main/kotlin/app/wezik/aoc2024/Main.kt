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
    val overrideLimits = argsMap["-f"]
    val quietMode = argsMap["-q"]

    fun log(message: String, isQuietAnswer: Boolean = false) {
        return when (quietMode) {
            "true" -> if (isQuietAnswer) println(message) else return
            else -> if (!isQuietAnswer) println(message) else return
        }
    }

    val solvers = if (day == null) {
        log("Running all days in order")
        StaticSolverSelector().selectAll()
    } else {
        listOf(StaticSolverSelector().select(day))
    }

    solvers.forEach { solverSource ->
        val inputPath = inputArg ?: solverSource.path
        val input = readFrom(inputPath)
        if (benchmarkArg != null) {
            var benchmarkRuns = benchmarkArg.toInt()
            // capping runs for some days to avoid long-running benchmarks
            val override = overrideLimits != null && overrideLimits == "true"
            if (hardcodedRunLimits.containsKey(solverSource) && !override) {
                benchmarkRuns = minOf(benchmarkRuns, hardcodedRunLimits[solverSource]!!)
            }
            val day = if (solverSource.ordinal < 9) "0${solverSource.ordinal + 1}" else solverSource.ordinal + 1
            var answer = ""
            val totalTime = time {
                log("[Day $day]:")
                val (p1Time, p2Time) = runBenchmark(solverSource, input, benchmarkRuns)
                log("  Part 1 average: ${p1Time.formatToMs()}")
                log("  Part 2 average: ${p2Time.formatToMs()}")
                answer += "Day$day,${p1Time.formatToMs()},${p2Time.formatToMs()}"
            }.toNanoDuration()
            log("  Total runs: $benchmarkRuns")
            log("  Total time: $totalTime (${totalTime.formatToSeconds()})")
            answer += ",$benchmarkRuns,${totalTime.formatToSeconds()}"
            log(answer, isQuietAnswer = true)
            return@forEach
        }
        val solution = solverSource.solver.solve(input)
        log(" Part 1 solution in ${solution.part1.time.formatToSeconds()}: \"${solution.part1.result}\"")
        log(" Part 2 solution in ${solution.part2.time.formatToSeconds()}: \"${solution.part2.result}\"")
        val answer = "Day${solverSource.ordinal + 1},${solution.part1.time.formatToMs()},${solution.part2.time.formatToMs()}"
        log(answer, isQuietAnswer = true)
    }
}

private fun Duration.formatToSeconds() = this.toString(DurationUnit.SECONDS, 3)
private fun Duration.formatToMs() = this.toString(DurationUnit.MILLISECONDS, 3)

private fun runBenchmark(source: SolverSource, input: List<String>, benchmarkRuns: Int): Pair<Duration, Duration> {
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
