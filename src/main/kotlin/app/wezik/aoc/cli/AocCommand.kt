package app.wezik.aoc.cli

import app.wezik.aoc.domain.FileDownloader
import app.wezik.aoc.domain.FileLoader
import app.wezik.aoc.domain.SolutionInput
import app.wezik.aoc.domain.SolutionSelector
import app.wezik.aoc.infrastructure.AocFileDownloader
import app.wezik.aoc.infrastructure.AocFileLoader
import app.wezik.aoc.infrastructure.ReflectionSolutionSelector
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.UsageError
import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.int
import com.github.ajalt.clikt.parameters.types.path
import java.time.LocalDateTime
import java.time.ZoneId
import java.io.File
import app.wezik.aoc.domain.Solution
import java.nio.file.Path
import app.wezik.aoc.domain.InputResolver

// entry point for the cli
fun start(args: Array<String>) = AocCommand.main(args)

private object AocCommand : CliktCommand("aoc") {
    // manual DI
    private val selector: SolutionSelector = ReflectionSolutionSelector()
    private val inputResolver = InputResolver(
        fileLoader = AocFileLoader(),
        fileDownloader = AocFileDownloader(),
    ) { msg -> echo(msg) }

    // options
    private val day by option("-d", "--day", help = "Day").int().required()
    private val year by option("-y", "--year", help = "Year (defaults to the most recent)").int()
    private val sessionCookie by option("-s", "--session-cookie", help = "Session cookie")
    private val example by option("-t", "--test", help = "Runs against example file").flag()
    private val path by option("-p", "--path", help = "Path with custom input file to load")
        .path(mustExist = true, canBeFile = true, canBeDir = false, mustBeReadable = true)

    // advent of code starts in december, so adjust date accordingly if necessary
    private fun mostRecentYear(): Int {
        // advent of code launches at midnight EST time
        val estZoneId = ZoneId.of("America/New_York")
        val zonedDateTime = LocalDateTime.now(estZoneId)
        return if (zonedDateTime.monthValue == 12) zonedDateTime.year else zonedDateTime.year - 1
    }

    override fun run() {
        val year = year ?: mostRecentYear()

        if (example && path != null) throw UsageError("cannot use both --test and --path options")

        val solution = selector.select(day, year) ?: throw UsageError("day $day of $year is not implemented")
        val input = when {
            example -> inputResolver.fetchExampleInput(day, year)
            path != null -> inputResolver.fetchCustomInput(day, year, path.toString())
            else -> inputResolver.fetchAdventInput(day, year, sessionCookie ?: System.getenv("ADVENT_COOKIE"))
        }

        val p1 = solution.part1(input)
        if (p1.isBlank()) echo("Part 1 not implemented") else echo("Part 1: $p1")

        val p2 = solution.part2(input)
        if (p2.isBlank()) echo("Part 2 not implemented") else echo("Part 2: $p2")
    }
}
