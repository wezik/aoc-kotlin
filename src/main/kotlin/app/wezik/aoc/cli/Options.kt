package app.wezik.aoc.cli

import app.wezik.aoc.domain.Day
import app.wezik.aoc.domain.Year
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.int
import com.github.ajalt.clikt.parameters.types.path

fun CliktCommand.dayOption() =
    option("-d", "--day", help = "Day")
        .int()
        .convert { Day(it) }
        .required()

fun CliktCommand.yearOption() =
    option("-y", "--year", help = "Year (defaults to last advent of code year)")
        .int()
        .convert { Year(it) }
        .default(Year.recent())

fun CliktCommand.sessionCookieOption() =
    option("-s", "--session-cookie", help = "Session cookie (defaults to \"ADVENT_COOKIE\" env variable)")

fun CliktCommand.pathOption() =
    option("-p", "--path", help = "Path with custom input file to load")
        .path(mustExist = true, canBeFile = true, canBeDir = false, mustBeReadable = true)
