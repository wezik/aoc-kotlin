package app.wezik.aoc

import java.time.LocalDate
import java.io.File

class Config(args: Array<String>) {
    private val argsMap = args.toList().chunked(2).associate { it[0] to it[1] }
    val year = parseYear(argsMap["-y"])
    val day = parseDay(argsMap["-d"])
    val input = parseInput(argsMap["-i"])

    private fun parseYear(input: String?): Int {
        if (input == null) return LocalDate.now().year
        return input.toInt()
    }

    private fun parseDay(input: String?): Int {
        if (input == null) return LocalDate.now().dayOfYear
        return input.toInt()
    }

    private fun parseInput(input: String?): File {
        if (input == null) error("No input provided")
        return File(input).also { if (!it.exists()) error("Input file does not exist: $input") }
    }
}
