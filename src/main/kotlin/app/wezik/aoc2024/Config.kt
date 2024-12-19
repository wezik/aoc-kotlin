package app.wezik.aoc2024

import app.wezik.aoc2024.solution.StaticSolverSelector
import app.wezik.aoc2024.solution.StaticSolverSelector.SolverSource

enum class RunMode {
    DEFAULT,
    BENCHMARK
}

class Config(args: Array<String>) {
    private val argsMap = args.toList().chunked(2).associate { it[0] to it[1] }

    private val days = argsMap["-days"]?.split(",")?.flatMap { parseDayRange(it) } ?: StaticSolverSelector().selectAll()
    private val inputs = argsMap["-input"]?.split(",")
    val solversToRun = mergeDaysAndInputs(days, inputs)

    val runMode = if (argsMap["-benchmark"] != null) RunMode.BENCHMARK else RunMode.DEFAULT

    val benchmarkRuns = argsMap["-benchmark"]?.toInt() ?: 0

    // Used it for piping output to CI CD and building markdown from it,
    // now I am using [MarkdownFormatter] for that purpose, consider removing
    val quietMode = argsMap["-q"]?.equals("true") == true

    val overrideLimits = argsMap["-override"]?.equals("true") == true
    fun getRunLimit(day: SolverSource) = when (day) {
        SolverSource.DAY06 -> 10
        SolverSource.DAY11 -> 500
        SolverSource.DAY14 -> 100
        SolverSource.DAY16 -> 100
        else -> 1000
    }

    // only Markdown format is supported for now
    val format = argsMap["-format"]?.equals("markdown") == true
    val formatter = MarkdownFormatter(runMode)
}

private fun parseDayRange(input: String): List<SolverSource> {
    return input.split("-").map { it.toInt() }.let { split ->
        if (split.size == 1) return listOf(StaticSolverSelector().select(split[0]))
        val (start, end) = split
        (start..end).map { StaticSolverSelector().select(it) }
    }
}

private fun mergeDaysAndInputs(days: List<SolverSource>, inputs: List<String>?): List<Pair<SolverSource, String>> {
    val resultList = mutableListOf<Pair<SolverSource, String>>()
    val paths = inputs?.toMutableList() ?: mutableListOf()
    for (day in days) {
        val path = paths.removeFirstOrNull()
        if (path == null || path == "0") resultList.add(day to day.path)
        else resultList.add(day to path)
    }
    return resultList
}
