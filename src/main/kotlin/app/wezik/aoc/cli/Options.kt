package app.wezik.aoc.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.options.validate
import com.github.ajalt.clikt.parameters.types.int
import com.github.ajalt.clikt.parameters.types.path

fun CliktCommand.dayOption() =
    option("-d", "--day", help = "Day")
        .int()
        .required()
        .validate { require(it in 1..25) { "Day must be between 1 and 25" } }

fun CliktCommand.yearOption() =
    option("-y", "--year", help = "Year (defaults to last advent of code year)")
        .int()
        .validate { require(it >= 2015) { "Advent of code started in 2015" } }

fun CliktCommand.sessionCookieOption() =
    option("-s", "--session-cookie", help = "Session cookie (defaults to \"ADVENT_COOKIE\" env variable)")

fun CliktCommand.pathOption() =
    option("-p", "--path", help = "Path with custom input file to load")
        .path(mustExist = true, canBeFile = true, canBeDir = false, mustBeReadable = true)
