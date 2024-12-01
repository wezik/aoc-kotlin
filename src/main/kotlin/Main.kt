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

    val p1Times = mutableListOf<Long>()
    val p2Times = mutableListOf<Long>()
    (0 until 100).forEach {
        val solution = solver.solve(readFrom(path))
        if (solution.first.time.inWholeMicroseconds > 1) {
            p1Times.add(solution.first.time.inWholeMicroseconds)
        }
        if (solution.second.time.inWholeMicroseconds > 1) {
            p2Times.add(solution.second.time.inWholeMicroseconds)
        }
    }
    val solution = solver.solve(readFrom(path))
    println("Part 1: ${solution.first.value} in ${p1Times.average().toMs()} for ${p1Times.size} runs")
    println("Part 2: ${solution.second.value} in ${p2Times.average().toMs()} for ${p2Times.size} runs")
}

private fun Double.toMs(): String {
    return String.format("%.3f ms average", this / 1000.0)
}
