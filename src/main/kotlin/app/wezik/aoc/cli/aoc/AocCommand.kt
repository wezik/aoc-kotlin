package app.wezik.aoc.cli.aoc

import app.wezik.aoc.cli.dayOption
import app.wezik.aoc.cli.sessionCookieOption
import app.wezik.aoc.cli.yearOption
import app.wezik.aoc.domain.*
import app.wezik.aoc.infrastructure.AocFileDownloader
import app.wezik.aoc.infrastructure.AocFileLoader
import app.wezik.aoc.infrastructure.ReflectionSolutionSelector
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.main

// NOTE: CLI entrypoint
fun main(args: Array<String>): Unit = AocCommand.main(args)

private object AocCommand : CliktCommand("aoc") {
    // manual DI
    private val solutionRunner = SolutionRunner(
        solutionSelector = ReflectionSolutionSelector(),
        inputResolver = InputResolver(
            fileLoader = AocFileLoader(),
            fileDownloader = AocFileDownloader(),
            echo = { msg -> echo(msg) }
        ),
        echo = { msg -> echo(msg) }
    )

    // options
    private val day by dayOption()
    private val year by yearOption()
    private val sessionCookie by sessionCookieOption()

    override fun run() {
        val ctx = DefaultContext(
            day = Day(day),
            year = year?.let { Year(it) } ?: Year.recent(),
            sessionCookie = sessionCookie ?: System.getenv("ADVENT_COOKIE"),
        )

        val result = solutionRunner.run(ctx)
    }
}
