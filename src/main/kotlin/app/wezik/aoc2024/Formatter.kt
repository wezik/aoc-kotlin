package app.wezik.aoc2024

import java.io.File
import java.time.Instant
import kotlin.time.DurationUnit
import kotlin.time.toDuration

interface Formatter {
    fun append(input: String)
    fun appendSolution(day: String, part1: String, part2: String)
    fun appendBenchmark(day: String, part1: String, part2: String, runs: Int, totalTime: String)
    fun build(path: String)
}

class MarkdownFormatter(runMode: RunMode) : Formatter {
    private val start = Instant.now()

    private val buffer = StringBuilder().apply {
        if (runMode == RunMode.BENCHMARK) {
            append("# Benchmark results\n")
            append("Benchmark started at $start\n")
            append("\n")
            append("| Day | Part 1 | Part 2 | Total Runs | Total time |\n")
            append("|-----|--------|--------|------------|------------|\n")
        } else {
            append("# Solution results\n")
            append("Solver started at $start\n")
            append("\n")
            append("| Day | Part 1 | Part 2 |\n")
            append("|-----|--------|--------|\n")
        }
    }

    override fun append(input: String) {
        buffer.append("$input\n")
    }

    override fun appendSolution(day: String, part1: String, part2: String) {
        buffer.append("| $day | $part1 | $part2 |\n")
    }

    override fun appendBenchmark(day: String, part1: String, part2: String, runs: Int, totalTime: String) {
        buffer.append("| $day | $part1 | $part2 | $runs | $totalTime |\n")
    }

    override fun build(path: String) {
        buffer.append("\n")
        val finish = Instant.now()
        val elapsed = (finish.toEpochMilli() - start.toEpochMilli()).toDuration(DurationUnit.MILLISECONDS)
        buffer.append("Finished at $finish elapsed time: ($elapsed)")
        val file = File("$path.md")
        file.writeText(buffer.toString())
    }
}
