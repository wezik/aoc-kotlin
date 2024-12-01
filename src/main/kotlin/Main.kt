package org.example

import org.example.solution.solver.Day1Solver

fun main(args: Array<String>) {

    if (args.size != 2) {
        println("Usage: kotlin Main.kt <day> <url>")
        return
    }
    val (day, path) = args

    val solver = when (day) {
        "1" -> Day1Solver()
        else -> throw IllegalArgumentException("Day $day not supported")
    }

    println("Result: ${solver.solve(path)}")
}