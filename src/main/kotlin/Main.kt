package org.example

import org.example.solution.readFrom
import org.example.solution.solver.Day1Solver

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

    val isBenchmark = System.getenv("AOC_BENCHMARK") ?: "false"
    if (isBenchmark == "true") {
        println("Running benchmark mode")
        val p1Times = mutableListOf<Long>()
        val p2Times = mutableListOf<Long>()
        (1..1000).forEach {
            val solution = solver.solve(readFrom(path))
            p1Times.add(solution.first.time.inWholeMicroseconds)
            p2Times.add(solution.second.time.inWholeMicroseconds)
        }
        val solution = solver.solve(readFrom(path))
        println("Part 1: \"${solution.first.value}\" in ${p1Times.average().toMs()} average for ${p1Times.size} runs")
        println("Part 2: \"${solution.second.value}\" in ${p2Times.average().toMs()} average for ${p2Times.size} runs")
    } else {
        val solution = solver.solve(readFrom(path))
        println("Part 1: \"${solution.first.value}\" in ${solution.first.time.inWholeMicroseconds.toDouble().toMs()}")
        println("Part 2: \"${solution.second.value}\" in ${solution.second.time.inWholeMicroseconds.toDouble().toMs()}")
    }
}

private fun Double.toMs(): String {
    return String.format("%.3f ms", this / 1000.0)
}
