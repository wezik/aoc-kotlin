package app.wezik.aoc.cli

import app.wezik.aoc.domain.InputLoader
import app.wezik.aoc.domain.SolutionSelector
import app.wezik.aoc.infrastructure.AocInputLoader
import app.wezik.aoc.infrastructure.ReflectionSolutionSelector
import com.github.ajalt.clikt.core.CliktCommand
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
    private val inputLoader: InputLoader = AocInputLoader()

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
            echo("Day $day/$year is not implemented")
            return
        }

        val input = inputLoader.load(day, year) ?: let {
            echo("Failed to load input for $day/$year")
            return
        }

        solution.part1?.let { fn ->
            val answer = fn(input)
            echo("Part 1: $answer")
        } ?: echo("Part 1 not implemented")
        
        solution.part2?.let { fn ->
            val answer = fn(input)
            echo("Part 2: $answer")
        } ?: echo("Part 2 not implemented")
    }
}
