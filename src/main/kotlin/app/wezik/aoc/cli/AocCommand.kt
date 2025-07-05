package app.wezik.aoc.cli

import app.wezik.aoc.domain.FileLoader
import app.wezik.aoc.domain.SolutionInput
import app.wezik.aoc.domain.SolutionSelector
import app.wezik.aoc.infrastructure.AocFileLoader
import app.wezik.aoc.infrastructure.ReflectionSolutionSelector
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.UsageError
import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.int
import java.time.LocalDateTime
import java.time.ZoneId

// entry point for the cli
fun start(args: Array<String>) = AocCommand.main(args)

private object AocCommand : CliktCommand("aoc") {
    // manual DI
    private val selector: SolutionSelector = ReflectionSolutionSelector()
    private val fileLoader: FileLoader = AocFileLoader()

    // options
    private val day by option("-d", "--day", help = "Day").int().required()
    private val year by option("-y", "--year", help = "Year (defaults to the most recent)").int()

    // advent of code starts in december, so adjust date accordingly if necessary
    private fun mostRecentYear(): Int {
        // advent of code launches at midnight EST time
        val estZoneId = ZoneId.of("America/New_York")
        val zonedDateTime = LocalDateTime.now(estZoneId)
        return if (zonedDateTime.monthValue == 12) zonedDateTime.year else zonedDateTime.year - 1
    }

    override fun run() {
        val year = year ?: mostRecentYear()

        val solution = selector.select(day, year) ?: let {
            throw UsageError("Day $day of $year is not implemented")
        }

        val file = fileLoader.load(day, year) ?: let {
            throw UsageError("Failed to load input for day $day of $year")
        }

        val input = SolutionInput(file)

        val p1 = solution.part1(input)
        if (p1.isBlank()) echo("Part 1 not implemented") else echo("Part 1: $p1")

        val p2 = solution.part2(input)
        if (p2.isBlank()) echo("Part 2 not implemented") else echo("Part 2: $p2")
    }
}
