package org.example

import org.example.solution.reader.Day1Reader
import org.example.solution.solver.Day1Solver

fun main(args: Array<String>) {

    if (args.size != 2) {
        println("Usage: kotlin Main.kt <day> <url>")
        return
    }
    val (day, path) = args

    val (solver, reader) = when (day) {
        "1" -> Pair(Day1Solver(), Day1Reader())
        else -> throw IllegalArgumentException("Day $day not supported")
    }

    val solution = solver.solve(reader.read(path))
    println("Result: ${solution.first} and ${solution.second}")
}